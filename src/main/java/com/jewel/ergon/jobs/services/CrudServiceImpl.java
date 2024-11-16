package com.jewel.ergon.jobs.services;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public abstract class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    // Abstract method for retrieving the specific repository
    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity); // Save or update entity in the database
    }

    @Override
    public Optional<T> findById(ID id) {
        return getRepository().findById(id); // Find entity by its ID
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll(); // Retrieve all entities
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id); // Delete entity by its ID
    }
}
