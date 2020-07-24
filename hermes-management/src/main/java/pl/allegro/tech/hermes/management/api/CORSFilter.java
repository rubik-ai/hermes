package pl.allegro.tech.hermes.management.api;

import org.springframework.beans.factory.annotation.Autowired;
import pl.allegro.tech.hermes.management.config.CorsProperties;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    private final CorsProperties corsProperties;

    @Autowired
    public CORSFilter(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
//        headers.add("Access-Control-Allow-Origin", corsProperties.getAllowedOrigin());
        List<String> originList = Arrays.asList("http://metis.rubik.ai", "http://dev.rubik.ai", "http://localhost:3000", "*.dataos.io","http://127.0.0.1:3000");
        String origin = requestContext.getHeaderString("Origin");
        if (originList.contains(origin)) {
            headers.add("Access-Control-Allow-Origin", origin);
        }
        headers.add("Access-Control-Allow-Credentials", true);
        headers.add("Access-Control-Allow-Methods", "OPTIONS,POST,PUT,GET,HEAD,DELETE");
        headers.add("Access-Control-Max-Age", "1209600");
        headers.addAll(
                "Access-Control-Allow-Headers",
                "X-Requested-With",
                "Content-Type",
                "Accept",
                "Origin",
                "Authorization",
                "Hermes-Admin-Password"
        );
    }
}