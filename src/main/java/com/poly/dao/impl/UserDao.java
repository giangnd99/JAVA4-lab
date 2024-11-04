package com.poly.dao.impl;

import com.poly.dao.GenericDao;
import com.poly.dao.JpaDao;
import com.poly.entity.User;
import jakarta.persistence.Query;

import java.util.List;

public class UserDao extends JpaDao<User> implements GenericDao<User> {

    public UserDao() {
        super();
    }

    @Override
    public User get(Object id) {
        return super.findById(User.class, id);
    }

    @Override
    public void delete(Object id) {
        super.deleteById(User.class, id);
    }

    @Override
    public List<User> listAll() {
        return super.findAll(User.class);
    }

    @Override
    public long count() {
        String jpql = "SELECT COUNT(u) FROM User u";
        Long execute = (Long) execute(entityManager -> entityManager.createQuery(jpql).getSingleResult());
        return execute;
    }

    public List<User> paginate(int page, int size) {
        return super.paginate(User.class, page, size);
    }

    public long countWithFilter(String filter) {
        String jpql = "SELECT COUNT(u) FROM User u";
        jpql = jpql + " WHERE u.email LIKE :email AND u.admin = :role";
        String finalJpql = jpql;
        return (long) super.execute(entityManager -> {
            Query query = entityManager.createQuery(finalJpql);
            query.setParameter("email", "%" + filter + "%");
            query.setParameter("role", false);
            return (long) query.getSingleResult();
        });
    }

    public List<User> findUsersByEmailAndRole(int pageNumber, int pageSize) {
        String jpql = "SELECT o FROM User o WHERE o.email LIKE :search AND o.admin = :role";
        return super.execute(entityManager -> {
            Query query = entityManager.createQuery(jpql, User.class);
            query.setParameter("search", "%@fpt.edu.vn%");
            query.setParameter("role", false);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        });
    }
}
