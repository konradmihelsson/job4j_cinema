package com.example.store;

import com.example.model.Account;
import com.example.model.Session;
import com.example.model.Ticket;
import org.junit.*;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class DBStoreTest {
    static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = DBStoreTest.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    config.getProperty("jdbc.url"),
                    config.getProperty("jdbc.username"),
                    config.getProperty("jdbc.password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Before
    public void createAccount() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO accounts(username, email, phone) VALUES ('qqq', 'www', 'eee')")
        ) {
            ps.execute();
        }
    }

    @After
    public void wipeTables() throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM tickets;"
                        + "ALTER TABLE tickets ALTER COLUMN id RESTART WITH 1;"
                        + "DELETE FROM accounts;"
                        + "ALTER TABLE accounts ALTER COLUMN id RESTART WITH 1;")
        ) {
            ps.execute();
        }
    }

    @Test
    public void whenSellTwoTicketsOnSameSessionRowCell() {
        Store dbStore = DBStore.instOf();
        Session session = new Session(1);
        Account account = new Account(1, "Some Account");
        dbStore.addTicket(session, 1, 1, account);
        Ticket secondTicket = dbStore.addTicket(session, 1, 1, account);
        assertThat(secondTicket, nullValue());
    }

    @Test
    public void whenSellTwoDifferentTickets() {
        Store dbStore = DBStore.instOf();
        Session session = new Session(1);
        Account account = new Account(1, "Some Account");
        Ticket firstTicket = dbStore.addTicket(session, 1, 1, account);
        Ticket secondTicket = dbStore.addTicket(session, 2, 1, account);
        assertThat(firstTicket.getId(), is(1));
        assertThat(secondTicket.getId(), is(2));
    }
}
