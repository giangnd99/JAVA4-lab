package com.poly.controller.lab1;

import com.poly.service.UserServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/lab1")
public class lab1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserServices services = new UserServices(req, resp);
        try {

            int pageNumber = 1;
            String numberOfPage = (req.getParameter("pageNumber"));
            if (numberOfPage != null) {
                pageNumber = Integer.parseInt(numberOfPage);
            }
            int pageSize = 5;
            if (req.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(req.getParameter("pageSize"));
            }
            String filter = "all";
            if (req.getParameter("filter") != null) {
                filter = req.getParameter("filter");
            }

            services.loadListWithPagination(pageNumber, pageSize, filter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
