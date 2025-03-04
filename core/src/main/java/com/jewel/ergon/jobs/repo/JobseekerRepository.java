package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobseekerRepository extends JpaRepository<JobSeeker, Long>, JpaSpecificationExecutor<JobSeeker> {
}