package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandRepository extends JpaRepository<Demand, Long> {
}