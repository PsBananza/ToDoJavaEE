package com.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CreateToDoDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "com.example.controller.Servlet", value = "/text")
public class TextPutServlet extends HttpServlet {
    private final Service service = new Service();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> id = Optional.ofNullable(req.getParameter("id"));
        String i = req.getReader().lines().collect(Collectors.joining());
        CreateToDoDto objectMapper = new ObjectMapper().readValue(i, CreateToDoDto.class);
        service.changeCurrentText(objectMapper, Long.valueOf(id.get()));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    //    @PatchMapping("/text/{id}")
//    public BaseSuccessResponse changeCurrentStatus(@PathVariable Long id, @RequestBody CreateToDoDto text) {
//            service.changeCurrentText(id, text);
//            return new BaseSuccessResponse();
//    }
}
