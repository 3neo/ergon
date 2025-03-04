package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {

    Optional<AppConfig> findByKey(String key);
}
