package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.model.Skill;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class SkillService extends CrudServiceImpl<Skill, Long> {


    public SkillService(ApplicationContext applicationContext, SpecificationBuilder<Skill> specificationBuilder, JpaRepository<Skill, Long> jpaRepository, JpaSpecificationExecutor<Skill> specificationRepository) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
    }
}
