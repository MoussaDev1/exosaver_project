package com.ddmspringapp.exosaver.mapper;

import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceRequestDTO;
import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceResponseDTO;
import com.ddmspringapp.exosaver.model.Resource;

public class ResourceMapper {

    public static Resource toEntity(ResourceRequestDTO dto){
        Resource resource = new Resource();
        resource.setUrl(dto.getUrl());
        resource.setType(dto.getType());
        return resource;
    }

    public static ResourceResponseDTO toResponseDTO(Resource resource){
        ResourceResponseDTO dto = new ResourceResponseDTO();
        dto.setId(resource.getId());
        dto.setUrl(resource.getUrl());
        dto.setType(resource.getType());
        return dto;
    }
}
