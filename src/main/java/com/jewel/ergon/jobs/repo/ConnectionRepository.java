package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
}