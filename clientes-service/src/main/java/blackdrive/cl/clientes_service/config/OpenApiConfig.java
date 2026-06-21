package blackdrive.cl.clientes_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server gatewayServer = new Server();
        gatewayServer.setUrl("http://localhost:8080");
        gatewayServer.setDescription("API Gateway");

        Server clientesDirecto = new Server();
        clientesDirecto.setUrl("http://localhost:8091");
        clientesDirecto.setDescription("Clientes Service directo");

        Server pagosDirecto = new Server();
        pagosDirecto.setUrl("http://localhost:8092");
        pagosDirecto.setDescription("Pagos Service directo");

        return new OpenAPI()
                .servers(List.of(gatewayServer, clientesDirecto, pagosDirecto));
    }
}
