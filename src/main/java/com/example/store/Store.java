package com.example.store;

import com.example.model.Account;
import com.example.model.Session;
import com.example.model.Ticket;

import java.util.List;

public interface Store {

    List<Ticket> findAllTickets();

    Ticket addTicket(Session session, int row, int cell, Account account);
}
