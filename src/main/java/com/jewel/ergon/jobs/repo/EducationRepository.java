package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationRepository extends JpaRepository<Education, Long>, JpaSpecificationExecutor<Education> {
}