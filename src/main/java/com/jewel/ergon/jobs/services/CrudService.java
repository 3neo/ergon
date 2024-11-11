package com.jewel.ergon.jobs.services;


import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    T save(T entity); // Save a new entity or update an existing one

    Optional<T> findById(ID id); // Find an entity by its ID

    List<T> findAll(); // Retrieve all entities

    void deleteById(ID id); // Delete an entity by its ID
}
