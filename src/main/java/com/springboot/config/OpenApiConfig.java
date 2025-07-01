package com.springboot.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("STUDENT MANAGEMENT SYSTEM-REST API-02")
                        .description("API- Documentation for managing student data")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Rajkumar prasad")
                                .email("rajkumarprasad@gmail.com")
                                .url("https://github.com/RajKTH1415"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/RajKTH1415/Spring-Boot-RestApi-03.git"));
    }
}
