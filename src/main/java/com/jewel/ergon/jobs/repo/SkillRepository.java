package com.jewel.ergon.jobs.repo;

import com.jewel.ergon.jobs.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}