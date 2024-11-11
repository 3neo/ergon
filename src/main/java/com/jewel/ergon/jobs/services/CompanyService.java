package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.Company;
import com.jewel.ergon.jobs.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//TODO add similar services to other entities
@Service
public class CompanyService extends CrudServiceImpl<Company, Long> {



    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    protected CompanyRepository getRepository() {
        return companyRepository; // Provide the specific repository for Company entity
    }
}

