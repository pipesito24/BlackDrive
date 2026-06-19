package blackdrive.cl.sucursales_service.exception;

public class SucursalNoEncontradaException extends RuntimeException {
    public SucursalNoEncontradaException(Long id) {
        super("No existe una sucursal con id: " + id);
    }
}
