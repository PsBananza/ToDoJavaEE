package com.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ChangeStatusTodoDto;
import dto.CreateToDoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "n3", value = "/basic")
public class BasicServlet extends HttpServlet {

    private final Service service = new Service();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i = req.getReader().lines().collect(Collectors.joining());
        CreateToDoDto objectMapper = new ObjectMapper().readValue(i, CreateToDoDto.class);
        service.create(objectMapper);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> integer = Optional.ofNullable(req.getParameter("id"));
        if (integer.isPresent()) {
            service.deleteById(Long.valueOf(integer.get()));
        } else {
            service.deleteAllReady();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String i = req.getReader().lines().collect(Collectors.joining());
        ChangeStatusTodoDto objectMapper = new ObjectMapper().readValue(i, ChangeStatusTodoDto.class);
        service.changeStatusTodoDto(objectMapper);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.parseInt(req.getParameter("page"));
        int perPage = Integer.parseInt(req.getParameter("perPage"));
        Optional<String> status = Optional.ofNullable(req.getParameter("status"));
        PrintWriter pw = resp.getWriter();

        if (status.isPresent()) {
            pw.println(service.getPaginated(page, perPage, Boolean.valueOf(status.get())));
        } else {
            pw.println(service.getPaginated(page, perPage));
        }
    }

}
