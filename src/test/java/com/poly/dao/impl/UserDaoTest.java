package com.poly.dao.impl;

import com.poly.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDaoTest {

    @Test
    void listAll() {
        UserDao dao = new UserDao();
        List<User> list = dao.listAll();
        assertNotNull(list);
    }

    @Test
    void paginate() {
        UserDao dao = new UserDao();
        List<User> list = dao.paginate(1, 5);
        assertNotNull(list);
    }

    @Test
    void findUsersByEmailAndRole() {
    }

    @Test
    void count() {
        UserDao dao = new UserDao();
        long count = dao.count();
        assertNotNull(count);
    }

    @Test
    void countWithFilter() {
        UserDao dao = new UserDao();
        int count = (int) dao.countWithFilter("@fpt.edu.vn");
        assertNotNull(count);
    }

    @Test
    void testFindUsersByEmailAndRole() {
        UserDao dao = new UserDao();
        List<User> list = dao.findUsersByEmailAndRole(1, 5);
        assertNotNull(list);
    }
}