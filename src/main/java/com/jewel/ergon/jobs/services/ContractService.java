package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.model.Connection;
import com.jewel.ergon.jobs.model.Contract;
import com.jewel.ergon.jobs.repo.ConnectionRepository;
import com.jewel.ergon.jobs.repo.ContractRepository;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;


@Service
public class ContractService  extends CrudServiceImpl<Contract, Long>{


    @Autowired
    public ContractService(ContractRepository contractRepository, SpecificationBuilder<Contract> specificationBuilder, ApplicationContext applicationContext) {
        super(applicationContext, specificationBuilder, contractRepository, contractRepository);
    }
}
