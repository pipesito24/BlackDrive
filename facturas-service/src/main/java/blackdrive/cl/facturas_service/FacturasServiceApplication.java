package blackdrive.cl.facturas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FacturasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturasServiceApplication.class, args);
	}

}
