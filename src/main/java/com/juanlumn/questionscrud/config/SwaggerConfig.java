package com.juanlumn.questionscrud.config;

import static java.util.Collections.emptyList;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.base.Predicates;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration
 */
@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2).select().apis(Predicates.not(basePackage("org.springframework.boot"))).build()
            .apiInfo(apiInfo()).useDefaultResponseMessages(false);
    }

    /**
     * Method to set the basic info for the current api
     *
     * @return an ApiInfo object with the information about this service
     */
    private ApiInfo apiInfo() {
        return new ApiInfo("CRUD Questions Service ",
            "This service provides the end points needed to retrieve or save questions and get statistics", "0.1",
            "Terms of service", new Contact("juanlumn", "www.juanlumn.com", "juanlumn@gmail.com"), "", "", emptyList());
    }
}