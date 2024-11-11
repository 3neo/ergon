package com.jewel.ergon.cv.repo;

import com.jewel.ergon.cv.model.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Long> {
}