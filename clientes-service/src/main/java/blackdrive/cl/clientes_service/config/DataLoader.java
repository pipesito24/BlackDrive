package blackdrive.cl.clientes_service.config;

import blackdrive.cl.clientes_service.model.ClientesModel;
import blackdrive.cl.clientes_service.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClientesRepository clientesRepository;

    @Override
    public void run(String... args) throws Exception {
        if (clientesRepository.count() == 0) {
            ClientesModel c1 = new ClientesModel();
            c1.setNombre("Pedro González");
            c1.setCorreo("pedro@gmail.com");
            c1.setDireccion("Av. Providencia 123, Santiago");
            c1.setSueldo(1500000);
            clientesRepository.save(c1);

            ClientesModel c2 = new ClientesModel();
            c2.setNombre("Juan Pérez");
            c2.setCorreo("juan@gmail.com");
            c2.setDireccion("Calle Falsa 456, Valparaíso");
            c2.setSueldo(1900000);
            clientesRepository.save(c2);

            ClientesModel c3 = new ClientesModel();
            c3.setNombre("Felipe Castillo");
            c3.setCorreo("felipecastillo@gmail.com");
            c3.setDireccion("Los Aromos 789, Puente Alto");
            c3.setSueldo(1950000);
            clientesRepository.save(c3);
        }
    }
}
