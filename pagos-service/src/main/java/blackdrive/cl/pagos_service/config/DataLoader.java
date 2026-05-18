package blackdrive.cl.pagos_service.config;

import blackdrive.cl.pagos_service.model.PagosModel;
import blackdrive.cl.pagos_service.repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PagosRepository pagosRepository;

    @Override
    public void run(String... args) throws Exception {
        if (pagosRepository.count() == 0) {
            PagosModel p1 = new PagosModel();
            p1.setMonto(150000000);
            p1.setFecha(LocalDate.of(2024, 1, 15));
            p1.setMetodoPago("Transferencia");
            p1.setClienteId(1L);
            pagosRepository.save(p1);

            PagosModel p2 = new PagosModel();
            p2.setMonto(135000000);
            p2.setFecha(LocalDate.of(2024, 2, 20));
            p2.setMetodoPago("Crédito");
            p2.setClienteId(2L);
            pagosRepository.save(p2);

            PagosModel p3 = new PagosModel();
            p3.setMonto(170000000);
            p3.setFecha(LocalDate.of(2024, 3, 10));
            p3.setMetodoPago("Débito");
            p3.setClienteId(3L);
            pagosRepository.save(p3);
        }
    }
}
