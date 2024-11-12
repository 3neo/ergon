package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.Skill;
import com.jewel.ergon.jobs.repo.SkillRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillService extends CrudServiceImpl<Skill, Long> {


    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    protected SkillRepository getRepository() {
        return this.skillRepository; // Provide the specific repository for Company entity
    }
}
