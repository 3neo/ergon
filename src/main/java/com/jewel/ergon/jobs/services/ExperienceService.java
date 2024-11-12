package com.jewel.ergon.jobs.services;

import com.jewel.ergon.jobs.model.Experience;
import com.jewel.ergon.jobs.repo.ExperienceRepository;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService extends CrudServiceImpl<Experience, Long> {


    private final ExperienceRepository experienceRepository;


    public ExperienceService(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    protected ExperienceRepository getRepository() {
        return this.experienceRepository; // Provide the specific repository for Company entity
    }
}

