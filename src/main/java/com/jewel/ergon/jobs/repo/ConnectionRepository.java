package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConnectionRepository extends JpaRepository<Connection, Long>   , JpaSpecificationExecutor<Connection> {
}