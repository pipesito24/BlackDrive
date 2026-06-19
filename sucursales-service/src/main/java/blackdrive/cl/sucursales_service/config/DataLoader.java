package blackdrive.cl.sucursales_service.config;

import blackdrive.cl.sucursales_service.model.SucursalesModel;
import blackdrive.cl.sucursales_service.repository.SucursalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SucursalesRepository sucursalesRepository;

    @Override
    public void run(String... args) throws Exception {
        if (sucursalesRepository.count() == 0) {
            SucursalesModel s1 = new SucursalesModel();
            s1.setNombre("Sucursal Santiago Centro");
            s1.setDireccion("Av. Libertador 1234");
            s1.setCiudad("Santiago");
            s1.setTelefono("+56222345678");
            sucursalesRepository.save(s1);

            SucursalesModel s2 = new SucursalesModel();
            s2.setNombre("Sucursal Providencia");
            s2.setDireccion("Av. Providencia 567");
            s2.setCiudad("Santiago");
            s2.setTelefono("+56222987654");
            sucursalesRepository.save(s2);

            SucursalesModel s3 = new SucursalesModel();
            s3.setNombre("Sucursal Valparaíso");
            s3.setDireccion("Av. Brasil 890");
            s3.setCiudad("Valparaíso");
            s3.setTelefono("+56322456789");
            sucursalesRepository.save(s3);
        }
    }
}
