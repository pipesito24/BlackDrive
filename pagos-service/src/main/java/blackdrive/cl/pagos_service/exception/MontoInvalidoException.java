package blackdrive.cl.pagos_service.exception;

public class MontoInvalidoException extends RuntimeException {
    public MontoInvalidoException(Integer monto) {
        super("El monto del pago no es válido: " + monto);
    }
}
