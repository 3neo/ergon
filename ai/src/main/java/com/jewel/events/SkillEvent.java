package com.jewel.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SkillEvent {
    private String skillId;
    private String candidateName;
    private String skillName;
    private String skillLevel;
    private String skillDescription;
}
