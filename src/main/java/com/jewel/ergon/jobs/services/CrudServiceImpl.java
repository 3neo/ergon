package com.jewel.ergon.jobs.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public abstract class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    @Autowired
    private ApplicationContext applicationContext; // Inject ApplicationContext

    protected abstract JpaRepository<T, ID> getRepository();

    @Override
    @Transactional
    public T save(T entity) {
        resolveRelationships(entity);
        return getRepository().save(entity);
    }

    @SuppressWarnings("unchecked")
    private void resolveRelationships(T entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(ManyToOne.class)) {
                field.setAccessible(true);
                try {
                    Object relatedEntity = field.get(entity);
                    if (relatedEntity != null) {
                        Field idField = findIdField(relatedEntity.getClass());
                        idField.setAccessible(true);
                        ID id = (ID) idField.get(relatedEntity);

                        if (id == null) {
                            throw new IllegalArgumentException("Related entity ID cannot be null: " + field.getName());
                        }

                        // Retrieve repository dynamically from ApplicationContext
                        JpaRepository<?, ID> relatedRepository = getRepositoryFromContext(relatedEntity.getClass());
                        Object resolvedEntity = relatedRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Related entity not found: " + field.getName()));

                        field.set(entity, resolvedEntity);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to resolve relationships for field: " + field.getName(), e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private JpaRepository<?, ID> getRepositoryFromContext(Class<?> entityClass) {
        String repositoryBeanName = entityClass.getSimpleName().toLowerCase() + "Repository"; // Repository bean naming convention
        return (JpaRepository<?, ID>) applicationContext.getBean(repositoryBeanName);
    }

    private Field findIdField(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No @Id field found in class: " + clazz.getName());
    }

    @Override
    public Optional<T> findById(ID id) {
        return getRepository().findById(id); // Find entity by its ID
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll(); // Retrieve all entities
    }

    @Transactional
    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id); // Delete entity by its ID
    }
}
