package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Education;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class EducationService extends CrudServiceImpl<Education, Long> {

    public EducationService(ApplicationContext applicationContext, SpecificationBuilder<Education> specificationBuilder, JpaRepository<Education, Long> jpaRepository, JpaSpecificationExecutor<Education> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}
