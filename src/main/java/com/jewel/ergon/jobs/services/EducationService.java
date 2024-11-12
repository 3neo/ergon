package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Demand;
import com.jewel.ergon.jobs.model.Education;
import com.jewel.ergon.jobs.repo.DemandRepository;
import com.jewel.ergon.jobs.repo.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationService extends CrudServiceImpl<Education, Long> {


    private final EducationRepository educationRepository;




    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    protected EducationRepository getRepository() {
        return this.educationRepository; // Provide the specific repository for Company entity
    }
}
