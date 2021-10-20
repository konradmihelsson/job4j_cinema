package com.example.store;

import com.example.model.Account;
import com.example.model.Session;
import com.example.model.Ticket;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class DBStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(DBStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private DBStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(DBStore.class.getClassLoader()
                        .getResourceAsStream("db.properties"))
                )
        )) {
            cfg.load(io);
        } catch (IOException e) {
            LOG.error("Exception logging", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            LOG.error("Exception logging", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new DBStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List<Ticket> findAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("SELECT t.id tid, t.session_id tsid, t.line tline, t.seat tseat, "
                             + "a.id accid, a.username accname "
                             + "FROM tickets t join accounts a on a.id = t.account_id")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(it.getInt("tid"), new Session(it.getInt("tsid")),
                            it.getInt("tline"), it.getInt("tseat"),
                            new Account(it.getInt("accid"), it.getString("accname"))));
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception logging", e);
        }
        return tickets;
    }

    @Override
    public Ticket addTicket(Session session, int line, int seat, Account account) {
        Ticket result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("INSERT INTO tickets (session_id, line, seat, account_id) VALUES (?, ?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, session.getId());
            ps.setInt(2, line);
            ps.setInt(3, seat);
            ps.setInt(4, account.getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    result = new Ticket(id.getInt(1), session, line, seat, account);
                }
            }
        } catch (SQLException e) {
            LOG.error("Exception logging", e);
        }
        return result;
    }
}
