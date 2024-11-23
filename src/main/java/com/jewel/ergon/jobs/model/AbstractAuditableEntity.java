package com.jewel.ergon.jobs.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;


@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class) // Enables JPA auditing
@Getter
@Setter
@ToString
@Where(clause = "deleted = false") // Automatically excludes soft-deleted records
public abstract class AbstractAuditableEntity implements Serializable {

//TODO fix this double id issue

  //  @Type(value = ) // Use BINARY(16) in production databases for efficiency
    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private String uuid;

    @CreatedDate
    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @CreatedBy
    @NotNull
    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Version
    @Column(name = "version", nullable = false)
    private long version; // Changed to long for high-concurrency systems

  //  @JsonIgnore // Exclude from serialization to ensure tenant security
    @NotNull
    @Column(name = "tenant_id", nullable = false, length = 50)
    private String tenantId; // For multi-tenancy


    //TODO ?
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "jsonb") // Specifies that this column is of type jsonb in the database
    @Type(JsonType.class)
    @ToString.Exclude              // Hibernate-specific annotation to handle jsonb types
    private Map<String, Object> attr;

    /**
     * Soft delete the entity.
     */
    public void softDelete() {
        this.deleted = true;
    }

    /**
     * Hook for additional initialization logic after loading.
     */
    @PostLoad
    protected void onLoad() {
        // Custom initialization logic
    }

    /**
     * Hook for pre-persistence logic.
     */
    @PrePersist
    protected void beforePersist() {
        // Custom pre-persist actions
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    /**
     * Hook for pre-update logic.
     */
    @PreUpdate
    protected void beforeUpdate() {
        // Custom pre-update actions
    }

    /**
     * Enforces immutability of createdAt field.
     *
     * @param createdAt Cannot modify after set.
     */
    public void setCreatedAt(ZonedDateTime createdAt) {
        if (this.createdAt == null) {
            this.createdAt = createdAt;
        }
    }
}
