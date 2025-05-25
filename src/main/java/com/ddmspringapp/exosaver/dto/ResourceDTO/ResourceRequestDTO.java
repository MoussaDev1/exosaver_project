package com.ddmspringapp.exosaver.dto.ResourceDTO;

import lombok.Data;

@Data
public class ResourceRequestDTO {
    private String url;
    private String type;
    private Long courseId;
    private Long exerciseId;
}