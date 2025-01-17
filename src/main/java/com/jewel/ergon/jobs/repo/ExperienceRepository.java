package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExperienceRepository extends JpaRepository<Experience, Long> , JpaSpecificationExecutor<Experience> {
}