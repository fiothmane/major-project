package com.emse.spring.faircorp.DAO;

import com.emse.spring.faircorp.model.AutoLight;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AutoLightDaoImpl implements AutoLightDaoCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public AutoLight findAutoLightById(Long id) {
        String jpql = "select autoLight from AutoLight autoLight where autoLight.id = :value";
        return em.createQuery(jpql, AutoLight.class)
                .setParameter("value", id)
                .getSingleResult();
    }
}
