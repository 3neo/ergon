package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
}