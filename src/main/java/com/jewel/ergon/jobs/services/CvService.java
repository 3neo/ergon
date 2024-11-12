package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Cv;
import com.jewel.ergon.jobs.repo.CvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CvService extends CrudServiceImpl<Cv, Long> {



    private final CvRepository cvRepository;


    @Autowired
    public CvService(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    @Override
    protected JpaRepository<Cv, Long> getRepository() {
        return this.cvRepository; // Provide the specific repository for Company entity
    }
}

