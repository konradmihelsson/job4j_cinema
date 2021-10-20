package com.example.servlet;

import com.example.model.Account;
import com.example.model.Session;
import com.example.model.Ticket;
import com.example.store.DBStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HallServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(DBStore.instOf().findAllTickets());
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String[] radioId = req.getParameter("place").split("-");

        Ticket ticket = DBStore.instOf().addTicket(new Session(1), Integer.parseInt(radioId[0]),
                        Integer.parseInt(radioId[1]), new Account(1, "name"));
        if (ticket == null) {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "This seat is already taken");
        } else {
            req.getSession().setAttribute("ticket", ticket);
            resp.sendRedirect("/payment");
        }
    }
}
