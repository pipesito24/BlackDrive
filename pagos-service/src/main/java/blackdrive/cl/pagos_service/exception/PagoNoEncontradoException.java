package blackdrive.cl.pagos_service.exception;

public class PagoNoEncontradoException extends RuntimeException {
    public PagoNoEncontradoException(Long id) {
        super("No existe un pago con id: " + id);
    }
}
