package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyRepository extends JpaRepository<Company, Long> , JpaSpecificationExecutor<Company> {


}