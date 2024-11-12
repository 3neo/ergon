package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.repo.CompanyRepository;
import com.jewel.ergon.jobs.repo.DemandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DemandService extends CrudServiceImpl<Demand, Long> {


    private final DemandRepository demandRepository;


    @Autowired
    public DemandService(DemandRepository demandRepository) {
        this.demandRepository = demandRepository;
    }

    @Override
    protected DemandRepository getRepository() {
        return this.demandRepository; // Provide the specific repository for Company entity
    }
}


