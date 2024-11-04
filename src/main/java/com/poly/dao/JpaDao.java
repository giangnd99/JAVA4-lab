package com.poly.dao;

import com.poly.util.EntityManagerOperation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class JpaDao<E> {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;


    static {
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("polyoe");
    }

    public JpaDao() {
    }

    public E create(E entity) {
        return excuteInTransaction(entityManager -> {
            entityManager.persist(entity);
            entityManager.flush();
            entityManager.refresh(entity);
            return entity;
        });
    }

    public E update(E entity) {
        return excuteInTransaction(entityManager -> {
            entityManager.merge(entity);
            entityManager.flush();
            entityManager.refresh(entity);
            return entity;
        });
    }

    public Boolean deleteById(Class<E> type, Object id) {
        excuteInTransaction(entityManager -> {
            E entity = entityManager.find(type, id);
            entityManager.remove(entity);
            entityManager.flush();
            entityManager.refresh(entity);
            return true;
        });
        return false;
    }


    public E findById(Class<E> type, Object id) {
        return execute(entityManager -> {
            E entity = entityManager.find(type, id);
            if (entity != null) {
                entityManager.refresh(entity);
            }
            return entity;
        });
    }

    public List<E> findAll(Class<E> type) {
        String query = "select e from " + type.getName() + " e";
        return execute(entityManager -> entityManager.createQuery(query).getResultList());
    }

    public List<E> paginate(Class<E> type, int page, int size) {
        String jpql = "select e from " + type.getName() + " e";
        return execute(entityManager -> {
            Query query = entityManager.createQuery(jpql);
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);
            return query.getResultList();
        });
    }

    protected <R> R execute(EntityManagerOperation<R> operation) {
        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager()) {
            return operation.apply(entityManager);
        }
    }

    protected <R> R excuteInTransaction(EntityManagerOperation<R> operation) {
        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager()) {
            entityManager.getTransaction().begin();
            R result = operation.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        }
    }
}
