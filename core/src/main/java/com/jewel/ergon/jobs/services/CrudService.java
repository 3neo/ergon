package com.jewel.ergon.jobs.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CrudService<T, ID> {

    T save(T entity); // Save a new entity or update an existing one

    Optional<T> findById(ID id); // Find an entity by its ID

    Page<T> findAll(Pageable p); // Retrieve all entities

    void deleteById(ID id); // Delete an entity by its ID
}
