package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Cv;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class CvService extends CrudServiceImpl<Cv, Long> {


    public CvService(ApplicationContext applicationContext, SpecificationBuilder<Cv> specificationBuilder, JpaRepository<Cv, Long> jpaRepository, JpaSpecificationExecutor<Cv> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}

