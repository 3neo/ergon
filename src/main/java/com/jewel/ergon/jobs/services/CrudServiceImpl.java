package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.services.eql.FilterCriteria;
import com.jewel.ergon.jobs.services.eql.QueryParser;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Generic implementation of the CrudService interface, providing basic CRUD operations for entities.
 *
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 */
@Builder
public class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    private final ApplicationContext applicationContext; // Inject ApplicationContext
    private final SpecificationBuilder<T> specificationBuilder;
    private final JpaRepository<T, ID> jpaRepository;
    private final JpaSpecificationExecutor<T> specificationRepository;

    /**
     * Constructor to initialize the required dependencies.
     *
     * @param applicationContext      the application context
     * @param specificationBuilder    the specification builder
     * @param jpaRepository           the JPA repository
     * @param specificationRepository the JPA specification executor
     */
    protected CrudServiceImpl(ApplicationContext applicationContext, SpecificationBuilder<T> specificationBuilder, JpaRepository<T, ID> jpaRepository, JpaSpecificationExecutor<T> specificationRepository) {
        this.applicationContext = applicationContext;
        this.specificationBuilder = specificationBuilder;
        this.jpaRepository = jpaRepository;
        this.specificationRepository = specificationRepository;
    }

    /**
     * Filters entities based on the provided query and pagination information.
     *
     * @param query       the query string
     * @param entityClass the class of the entity
     * @param pageable    the pagination information
     * @return a page of entities matching the query
     */
    public Page<T> filter(String query, Class<T> entityClass, Pageable pageable) {
        List<FilterCriteria> filters = QueryParser.parse(query);
        Specification<T> specification = specificationBuilder.buildSpecification(filters);
        return specificationRepository.findAll(specification, pageable);
    }

    /**
     * Saves an entity to the database. Resolves relationships before saving.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    @Override
    @Transactional
    public T save(T entity) {
        resolveRelationships(entity);
        return jpaRepository.save(entity);
    }

    /**
     * Resolves relationships annotated with @ManyToOne by fetching the related entities from their respective repositories.
     *
     * @param entity the entity whose relationships are to be resolved
     */
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

    /**
     * Dynamically retrieves a repository bean from the ApplicationContext based on the entity class name.
     *
     * @param entityClass the class of the entity
     * @return the JPA repository for the entity
     */
    @SuppressWarnings("unchecked")
    private JpaRepository<?, ID> getRepositoryFromContext(Class<?> entityClass) {
        String repositoryBeanName = entityClass.getSimpleName().toLowerCase() + "Repository"; // Repository bean naming convention
        return (JpaRepository<?, ID>) applicationContext.getBean(repositoryBeanName);
    }

    /**
     * Finds the field annotated with @Id in the given class.
     *
     * @param clazz the class to search for the @Id field
     * @return the field annotated with @Id
     * @throws IllegalArgumentException if no @Id field is found
     */
    private Field findIdField(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(jakarta.persistence.Id.class)) {
                return field;
            }
        }
        throw new IllegalArgumentException("No @Id field found in class: " + clazz.getName());
    }

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity
     * @return an Optional containing the entity if found, or empty if not found
     */
    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id); // Find entity by its ID
    }

    /**
     * Retrieves all entities with pagination support.
     *
     * @param pageable the pagination information
     * @return a page of entities
     */
    public Page<T> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable); // Retrieve all entities
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     */
    @Transactional
    @Override
    public void deleteById(ID id) {
        jpaRepository.deleteById(id); // Delete entity by its ID
    }

}