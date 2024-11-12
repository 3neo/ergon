package com.jewel.ergon.jobs.repo;


import com.jewel.ergon.jobs.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Long> {
}