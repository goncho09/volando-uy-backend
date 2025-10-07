package com.app.DAOs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseDao<T, ID> {

    protected EntityManager em;
    private final Class<T> entityClass;

    public BaseDao(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public List<T> listar() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    public Map<String, T> obtener(Function<T, String> keyMapper) {
        return listar()
                .stream()
                .collect(Collectors.toMap(keyMapper, e -> e));
    }

    public T buscar(ID id) {
        return em.find(entityClass, id);
    }

    public void guardar(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void actualizar(T entity) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void eliminar(ID id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T entity = buscar(id);
            if (entity != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}
