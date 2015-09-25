package com.raysmond.blog.services;

import com.raysmond.blog.models.BaseModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of IModel interface
 *
 * @author: Raysmond
 */
@Repository
@Transactional
public class IModelImpl implements IModel{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public <T extends BaseModel> T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public <T extends BaseModel> T get(Class<T> clazz, Long id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public <T extends BaseModel> List<T> findAll(Class<T> clazz) {
        return entityManager
                .createQuery("from "+clazz.getName())
                .getResultList();
    }
}
