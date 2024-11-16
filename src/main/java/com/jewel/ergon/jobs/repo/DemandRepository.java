package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.function.Function;

public interface DemandRepository extends JpaRepository<Demand, Long> {

    @Query(
            value = "SELECT * FROM demand d WHERE d.status = :status",
            countQuery = "SELECT COUNT(d.id) FROM demand d WHERE d.status = :status",
            nativeQuery = true
    )
    Page<Demand> findDemandsByStatusPaged(@Param("status") Status status, Pageable pageable);


}