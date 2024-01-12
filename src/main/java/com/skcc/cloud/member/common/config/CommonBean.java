package com.skcc.cloud.member.common.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBean {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull()) // null 복사 안함
                .setFieldMatchingEnabled(true) // setter 없이도 맵핑 가능
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // setter 없이도 맵핑 가능
                .setMatchingStrategy(MatchingStrategies.STRICT); // 필드가 정확히 일치할 때 맵핑
        return modelMapper;
    }

}
