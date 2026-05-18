package blackdrive.cl.vehiculos_service.config;

import blackdrive.cl.vehiculos_service.model.VehiculosModel;
import blackdrive.cl.vehiculos_service.repository.VehiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VehiculosRepository vehiculosRepository;

    @Override
    public void run(String... args) throws Exception {
        if (vehiculosRepository.count() == 0) {
            VehiculosModel v1 = new VehiculosModel();
            v1.setMarca("Mercedes");
            v1.setModelo("Clase A");
            v1.setAno(2022);
            v1.setPrecio(39000000);
            v1.setClienteId(1L);
            vehiculosRepository.save(v1);

            VehiculosModel v2 = new VehiculosModel();
            v2.setMarca("Aston Martin");
            v2.setModelo("Vanquish");
            v2.setAno(2021);
            v2.setPrecio(95000000);
            v2.setClienteId(2L);
            vehiculosRepository.save(v2);

            VehiculosModel v3 = new VehiculosModel();
            v3.setMarca("BMW");
            v3.setModelo("i120");
            v3.setAno(2023);
            v3.setPrecio(180000000);
            v3.setClienteId(3L);
            vehiculosRepository.save(v3);
        }
    }
}
