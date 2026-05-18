package blackdrive.cl.inventario_service.config;

import blackdrive.cl.inventario_service.model.InventarioModel;
import blackdrive.cl.inventario_service.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (inventarioRepository.count() == 0) {
            InventarioModel i1 = new InventarioModel();
            i1.setNombreProducto("Filtro de aceite Toyota");
            i1.setCategoria("Filtros");
            i1.setStock(15);
            i1.setPrecioUnitario(12000);
            i1.setVehiculoId(1L);
            inventarioRepository.save(i1);

            InventarioModel i2 = new InventarioModel();
            i2.setNombreProducto("Pastillas de freno Honda");
            i2.setCategoria("Frenos");
            i2.setStock(8);
            i2.setPrecioUnitario(45000);
            i2.setVehiculoId(2L);
            inventarioRepository.save(i2);

            InventarioModel i3 = new InventarioModel();
            i3.setNombreProducto("Neumático BMW 205/55 R16");
            i3.setCategoria("Neumáticos");
            i3.setStock(4);
            i3.setPrecioUnitario(95000);
            i3.setVehiculoId(3L);
            inventarioRepository.save(i3);
        }
    }
}