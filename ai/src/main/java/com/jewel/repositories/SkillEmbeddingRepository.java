package com.jewel.repositories;

import com.jewel.model.SkillEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillEmbeddingRepository extends JpaRepository<SkillEmbedding, UUID> {
}