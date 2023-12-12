package com.earosslot.beccmusicservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Eduardo",
                        email = "earosselot@gmail.com"
                ),
                description = "OpenApi documentation for Musify Backend Challenge",
                title = "Musify - OpenApi specification",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/musify"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8080/musify"
                )
        }
)
public class OpenApiConfig {


}
