package blackdrive.cl.vendedores_service.config;

import blackdrive.cl.vendedores_service.model.VendedoresModel;
import blackdrive.cl.vendedores_service.repository.VendedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VendedoresRepository vendedoresRepository;

    @Override
    public void run(String... args) throws Exception {
        if (vendedoresRepository.count() == 0) {
            VendedoresModel v1 = new VendedoresModel();
            v1.setNombre("Carlos Muñoz");
            v1.setEmail("carlos@blackdrive.cl");
            v1.setTelefono("+56911111111");
            v1.setComision(0.5);
            v1.setSucursalId(1L);
            vendedoresRepository.save(v1);

            VendedoresModel v2 = new VendedoresModel();
            v2.setNombre("Ana Torres");
            v2.setEmail("ana@blackdrive.cl");
            v2.setTelefono("+56922222222");
            v2.setComision(0.7);
            v2.setSucursalId(2L);
            vendedoresRepository.save(v2);

            VendedoresModel v3 = new VendedoresModel();
            v3.setNombre("Luis Herrera");
            v3.setEmail("luis@blackdrive.cl");
            v3.setTelefono("+56933333333");
            v3.setComision(1.0);
            v3.setSucursalId(3L);
            vendedoresRepository.save(v3);
        }
    }
}
