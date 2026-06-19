package blackdrive.cl.servicios_service.config;

import blackdrive.cl.servicios_service.model.ServiciosModel;
import blackdrive.cl.servicios_service.repository.ServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ServiciosRepository serviciosRepository;

    @Override
    public void run(String... args) throws Exception {
        if (serviciosRepository.count() == 0) {
            ServiciosModel s1 = new ServiciosModel();
            s1.setTipoServicio("Mantención");
            s1.setDescripcion("Mantención preventiva 10.000 km");
            s1.setCosto(85000);
            s1.setFechaServicio(LocalDate.of(2024, 2, 20));
            s1.setEstado("COMPLETADO");
            s1.setVehiculoId(1L);
            serviciosRepository.save(s1);

            ServiciosModel s2 = new ServiciosModel();
            s2.setTipoServicio("Reparación");
            s2.setDescripcion("Cambio de amortiguadores delanteros");
            s2.setCosto(220000);
            s2.setFechaServicio(LocalDate.of(2024, 3, 8));
            s2.setEstado("COMPLETADO");
            s2.setVehiculoId(2L);
            serviciosRepository.save(s2);

            ServiciosModel s3 = new ServiciosModel();
            s3.setTipoServicio("Diagnóstico");
            s3.setDescripcion("Diagnóstico electrónico de motor");
            s3.setCosto(45000);
            s3.setFechaServicio(LocalDate.of(2024, 4, 12));
            s3.setEstado("PENDIENTE");
            s3.setVehiculoId(3L);
            serviciosRepository.save(s3);
        }
    }
}