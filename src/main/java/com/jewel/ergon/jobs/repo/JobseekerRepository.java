package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Jobseeker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobseekerRepository extends JpaRepository<Jobseeker, Long> {
}