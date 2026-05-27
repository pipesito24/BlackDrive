package blackdrive.cl.vehiculos_service.exception;

public class VehiculoNoEncontradoException extends RuntimeException {
    public VehiculoNoEncontradoException(Long id) {
        super("No existe un vehículo con id: " + id);
    }
}
