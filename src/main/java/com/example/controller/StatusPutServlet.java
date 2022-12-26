package com.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ChangeStatusTodoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "com.example.controller.ControllerServlet", value = "/status")
public class StatusPutServlet extends HttpServlet {

    private final Service service = new Service();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> id = Optional.ofNullable(req.getParameter("id"));
        String i = req.getReader().lines().collect(Collectors.joining());
        ChangeStatusTodoDto objectMapper = new ObjectMapper().readValue(i, ChangeStatusTodoDto.class);
        service.changeCurrentStatus(objectMapper, Long.valueOf(id.get()));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
