package blackdrive.cl.facturas_service.config;

import blackdrive.cl.facturas_service.model.FacturasModel;
import blackdrive.cl.facturas_service.repository.FacturasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private FacturasRepository facturasRepository;

    @Override
    public void run(String... args) throws Exception {
        if (facturasRepository.count() == 0) {
            FacturasModel f1 = new FacturasModel();
            f1.setNumeroFactura("F-0001");
            f1.setMontoNeto(100000);
            f1.setIva(19000);
            f1.setMontoTotal(119000);
            f1.setFechaEmision(LocalDate.of(2024, 3, 10));
            f1.setEstado("PAGADA");
            f1.setClienteId(1L);
            f1.setPagoId(1L);
            facturasRepository.save(f1);

            FacturasModel f2 = new FacturasModel();
            f2.setNumeroFactura("F-0002");
            f2.setMontoNeto(63025);
            f2.setIva(11975);
            f2.setMontoTotal(75000);
            f2.setFechaEmision(LocalDate.of(2024, 4, 5));
            f2.setEstado("PENDIENTE");
            f2.setClienteId(2L);
            f2.setPagoId(2L);
            facturasRepository.save(f2);

            FacturasModel f3 = new FacturasModel();
            f3.setNumeroFactura("F-0003");
            f3.setMontoNeto(42017);
            f3.setIva(7983);
            f3.setMontoTotal(50000);
            f3.setFechaEmision(LocalDate.of(2024, 4, 15));
            f3.setEstado("ANULADA");
            f3.setClienteId(3L);
            f3.setPagoId(3L);
            facturasRepository.save(f3);
        }
    }
}
