package pl.allegro.tech.hermes.management.config;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class JerseyResourceConfig extends ResourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(JerseyResourceConfig.class);

    public JerseyResourceConfig(JerseyProperties jerseyProperties) {
        packages(true, "pl.allegro.tech.hermes.management.api");

        for(String packageToScan : jerseyProperties.getPackagesToScan()) {
            packages(true, packageToScan);
            logger.info("Scanning Jersey resources in: {}", packageToScan);
        }
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, jerseyProperties.getFilterStaticContentRegexp());
        register(RolesAllowedDynamicFeature.class);
    }
    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }

    private void configureSwagger() {
        // Available at localhost:port/api/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("HERMES-SWAGGER");
        config.setTitle("*Hermes Apis*");
        config.setVersion("v1");
        config.setContact("hermes-pubsub");
        config.setSchemes(new String[] { "http", "https" });
        config.setBasePath("/");
        config.setResourcePackage("pl.allegro.tech.hermes.management.api");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
