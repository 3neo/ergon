package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}