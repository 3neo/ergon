package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Experience;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService extends CrudServiceImpl<Experience, Long> {

    public ExperienceService(ApplicationContext applicationContext, SpecificationBuilder<Experience> specificationBuilder, JpaRepository<Experience, Long> jpaRepository, JpaSpecificationExecutor<Experience> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}

