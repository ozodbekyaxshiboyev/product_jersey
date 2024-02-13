package uz.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import uz.web.entity.ProductType;

import java.io.IOException;
import java.net.URI;

/**
 * @author Yaxshiboyev Ozodbek
 */
public class Main {
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        final ResourceConfig resourceConfig = new ResourceConfig()
                .packages("uz.web")
                .property("jersey.config.server.persistence.unitName", "jpa");
        resourceConfig.register(CorsFilter.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), resourceConfig);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        ApplicationRunnable.runSqlCommands();
        System.out.printf("Jersey app started with endpoints available at "
                + "%s%nHit Ctrl-C to stop it...%n", BASE_URI);

        System.in.read();
        server.stop();
    }

}

