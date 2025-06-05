package com.jewel.ergon.jobs.services;


import com.jewel.ergon.jobs.events.SkillEvent;
import com.jewel.ergon.jobs.events.producers.SkillPublisher;
import com.jewel.ergon.jobs.model.Skill;
import com.jewel.ergon.jobs.services.eql.SpecificationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class SkillService extends CrudServiceImpl<Skill, Long> {

    private final SkillPublisher skillPublisher;

    public SkillService(ApplicationContext applicationContext, SpecificationBuilder<Skill> specificationBuilder, JpaRepository<Skill, Long> jpaRepository, JpaSpecificationExecutor<Skill> specificationRepository, SkillPublisher skillPublisher) {
        super(applicationContext, specificationBuilder, jpaRepository, specificationRepository);
        this.skillPublisher = skillPublisher;
    }

    /**
     * Saves an entity to the database. Resolves relationships before saving.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    @Override
    public Skill save(Skill entity) {
        Skill s = super.save(entity);
        if (s != null) {
            skillPublisher.publishJobApplication(
                    SkillEvent.builder()
                            .skillId(s.getId().toString())
                            .skillName(s.getName())
                            .skillDescription(s.getDescription())
                            .build()
            );
        }
        return s;
    }
}
