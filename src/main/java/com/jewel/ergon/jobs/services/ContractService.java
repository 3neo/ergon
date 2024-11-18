package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.model.Contract;
import com.jewel.ergon.jobs.repo.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class ContractService  extends CrudServiceImpl<Contract, Long>{

    private final ContractRepository contractRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }



    /**
     * @return ContractRepository
     */
    @Override
    protected JpaRepository<Contract, Long> getRepository() {
        return this.contractRepository;
    }
}
