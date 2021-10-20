package com.example.model;

public class Ticket {
    private final int id;
    private final Session session;
    private final int line;
    private final int seat;
    private final Account account;

    public Ticket(int id, Session session, int line, int seat, Account account) {
        this.id = id;
        this.session = session;
        this.line = line;
        this.seat = seat;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public int getLine() {
        return line;
    }

    public int getSeat() {
        return seat;
    }

    public Account getAccount() {
        return account;
    }
}
