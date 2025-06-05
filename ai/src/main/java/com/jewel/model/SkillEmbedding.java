package com.jewel.model;


import com.jewel.events.SkillEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skill_embeddings")
public class SkillEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String skillId;
    private String candidateName;
    private String skillName;
    private String skillLevel;
    private String skillDescription;

    // The actual pgvector field (float[] will be mapped manually)
    @Column(columnDefinition = "vector(1536)") // Adjust dimension as needed
    private float[] embedding;


    public SkillEmbedding(SkillEvent event, float[] embedding) {
        this.skillId = event.getSkillId();
        this.candidateName = event.getCandidateName();
        this.skillName = event.getSkillName();
        this.skillLevel = event.getSkillLevel();
        this.skillDescription = event.getSkillDescription();
        this.embedding = embedding;
    }

    // Getters/setters (can be generated)
}