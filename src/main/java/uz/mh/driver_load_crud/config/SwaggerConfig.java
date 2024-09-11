package uz.mh.driver_load_crud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {
    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("webflux-api")
                .pathsToMatch("/api/**")
                .build();
    }



}
