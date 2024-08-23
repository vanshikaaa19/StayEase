package com.crio.stayease.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuration class for setting up OpenAPI (Swagger) documentation for the StayEase application.
 * <p>
 * This class provides OpenAPI specifications including general information about the API,
 * server details, and security requirements. It is used to configure the OpenAPI documentation
 * which is accessible through Swagger UI.
 * </p>
 * <p>
 * The OpenAPI documentation includes:
 * <ul>
 *   <li>API information such as title, description, version, license, and terms of service.</li>
 *   <li>Server details for local and production environments.</li>
 *   <li>Security scheme configuration for JWT bearer authentication.</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://swagger.io/docs/specification/about/">OpenAPI Specification</a>
 * @see <a href="https://swagger.io/tools/swagger-ui/">Swagger UI</a>
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Pushpanshu Ranjan Singh",
                        email = "pushpanshuranjansingh@gmail.com",
                        url = "pushpanshu.com"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - StayEase",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://no-licence.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://pushpanshu.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
