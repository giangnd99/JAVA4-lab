package com.poly.service;

import com.poly.dao.impl.UserDao;
import com.poly.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class UserServices {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private UserDao userDao;

    public UserServices(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.userDao = new UserDao();
    }

    public void loadListWithPagination(int pageNumber, int pageSize, String filter) throws Exception {
        List<User> users;
        int totalRecords;

        if ("email".equals(filter)) {
            users = userDao.findUsersByEmailAndRole(pageNumber, pageSize);
            String emailRegex = "@fpt.edu.vn";
            totalRecords = (int) userDao.countWithFilter(emailRegex);
        } else {
            users = userDao.paginate(pageNumber, pageSize);
            totalRecords = (int) userDao.count();
        }
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        request.setAttribute("users", users);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("filter", filter);
        request.getRequestDispatcher("/lab1/lab1.jsp").forward(request, response);
    }
}
