package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;


@Service
public class CompanyService extends CrudServiceImpl<Company, Long> {


    public CompanyService(ApplicationContext applicationContext, SpecificationBuilder<Company> specificationBuilder, JpaRepository<Company, Long> jpaRepository, JpaSpecificationExecutor<Company> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }


}

