package com.kanban.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.name}")
    private String apiName;

    @Value("${api.description}")
    private String apiDescription;

    @Value("${api.version}")
    private String apiVersion;

    @Value("${api.contact.name}")
    private String apiContactName;

    @Value("${api.contact.email}")
    private String apiContactEmail;

    @Value("${api.license.name}")
    private String apiLicenseName;

    @Value("${api.local.url}")
    private String apiLocalUrl;

    private static final String LOCAL = "Local";

    @Bean
    public OpenAPI configuration() {
        return new OpenAPI()
                .info(this.customInfo())
                .addServersItem(new Server().url(apiLocalUrl).description(LOCAL));
    }

    private Info customInfo() {
        return new Info()
                .title(apiName)
                .version(apiVersion)
                .description(apiDescription)
                .contact(this.customContact());
    }

    private Contact customContact() {
        return new Contact().name(apiContactName).email(apiContactEmail);
    }

    private License customLicense() {
        return new License().name(apiLicenseName).url(apiUrl);
    }
}