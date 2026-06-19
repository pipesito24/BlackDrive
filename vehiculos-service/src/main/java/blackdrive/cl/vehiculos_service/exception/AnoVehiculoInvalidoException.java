package blackdrive.cl.vehiculos_service.exception;

public class AnoVehiculoInvalidoException extends RuntimeException {
    public AnoVehiculoInvalidoException(Integer ano) {
        super("El año del vehículo no es válido: " + ano);
    }
}
