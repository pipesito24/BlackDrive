package blackdrive.cl.financiamiento_service.config;

import blackdrive.cl.financiamiento_service.model.FinanciamientoModel;
import blackdrive.cl.financiamiento_service.repository.FinanciamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private FinanciamientoRepository financiamientoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (financiamientoRepository.count() == 0) {
            FinanciamientoModel f1 = new FinanciamientoModel();
            f1.setCuotas(48);
            f1.setMontoTotal(150000000);
            f1.setMontoCuota(3125000);
            f1.setFechaInicio(LocalDate.of(2024, 1, 15));
            f1.setEstado("Activo");
            f1.setClienteId(1L);
            f1.setVehiculoId(1L);
            financiamientoRepository.save(f1);

            FinanciamientoModel f2 = new FinanciamientoModel();
            f2.setCuotas(36);
            f2.setMontoTotal(135000000);
            f2.setMontoCuota(3750000);
            f2.setFechaInicio(LocalDate.of(2024, 2, 20));
            f2.setEstado("Activo");
            f2.setClienteId(2L);
            f2.setVehiculoId(2L);
            financiamientoRepository.save(f2);

            FinanciamientoModel f3 = new FinanciamientoModel();
            f3.setCuotas(60);
            f3.setMontoTotal(170000000);
            f3.setMontoCuota(2833330);
            f3.setFechaInicio(LocalDate.of(2024, 3, 10));
            f3.setEstado("Activo");
            f3.setClienteId(3L);
            f3.setVehiculoId(3L);
            financiamientoRepository.save(f3);
        }
    }
}
