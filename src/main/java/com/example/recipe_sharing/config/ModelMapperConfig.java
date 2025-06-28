package com.example.recipe_sharing.config;

import com.example.recipe_sharing.dto.response.RecipeResponse;
import com.example.recipe_sharing.entity.RecipeEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(ObjectMapper objectMapper) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        AbstractConverter<String, List<String>> jsonToListConverter = new AbstractConverter<>() {
            @Override
            protected List<String> convert(String source) {
                if (source == null || source.isEmpty()) {
                    return Collections.emptyList();
                }
                try {
                    return objectMapper.readValue(source, new TypeReference<>() {});
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to convert String to JSON", e);
                }
            }
        };

        modelMapper.addMappings(new PropertyMap<RecipeEntity, RecipeResponse>() {
            @Override
            protected void configure() {
                using(jsonToListConverter).map(source.getImageUrlsJson()).setImageUrls(null);
                map().setChefName(source.getCreatedBy().getName());
            }
        });

        return modelMapper;
    }
}