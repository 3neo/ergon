package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.JobSeeker;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class JobSeekerService extends CrudServiceImpl<JobSeeker, Long> {

    public JobSeekerService(ApplicationContext applicationContext, SpecificationBuilder<JobSeeker> specificationBuilder, JpaRepository<JobSeeker, Long> jpaRepository, JpaSpecificationExecutor<JobSeeker> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}
