package blackdrive.cl.pedidos_service.config;

import blackdrive.cl.pedidos_service.model.PedidosModel;
import blackdrive.cl.pedidos_service.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Override
    public void run(String... args) throws Exception {
        if (pedidosRepository.count() == 0) {
            PedidosModel p1 = new PedidosModel();
            p1.setDescripcion("Cambio de aceite y filtro");
            p1.setCantidad(1);
            p1.setPrecioUnitario(35000);
            p1.setFechaPedido(LocalDate.of(2024, 3, 10));
            p1.setEstado("COMPLETADO");
            p1.setClienteId(1L);
            pedidosRepository.save(p1);

            PedidosModel p2 = new PedidosModel();
            p2.setDescripcion("Revisión de frenos");
            p2.setCantidad(1);
            p2.setPrecioUnitario(75000);
            p2.setFechaPedido(LocalDate.of(2024, 4, 5));
            p2.setEstado("PENDIENTE");
            p2.setClienteId(2L);
            pedidosRepository.save(p2);

            PedidosModel p3 = new PedidosModel();
            p3.setDescripcion("Alineación y balanceo");
            p3.setCantidad(1);
            p3.setPrecioUnitario(50000);
            p3.setFechaPedido(LocalDate.of(2024, 4, 15));
            p3.setEstado("EN_PROCESO");
            p3.setClienteId(3L);
            pedidosRepository.save(p3);
        }
    }
}