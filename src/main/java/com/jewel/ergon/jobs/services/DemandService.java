package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.exceptions.IncompatibleSourceAndTargetFieldsTypesException;
import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.model.Status;
import com.jewel.ergon.jobs.repo.CompanyRepository;
import com.jewel.ergon.jobs.repo.DemandRepository;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import com.jewel.ergon.jobs.utilis.MappingData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.jewel.ergon.jobs.utilis.MappingData.getAndSet;

@Service
public class DemandService extends CrudServiceImpl<Demand, Long> {

    public DemandService(ApplicationContext applicationContext, SpecificationBuilder<Demand> specificationBuilder, JpaRepository<Demand, Long> jpaRepository, JpaSpecificationExecutor<Demand> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}


