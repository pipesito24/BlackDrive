package blackdrive.cl.sucursales_service.exception;

public class NombreSucursalInvalidoException extends RuntimeException {
    public NombreSucursalInvalidoException() {
        super("El nombre de la sucursal no puede estar vacío");
    }
}
