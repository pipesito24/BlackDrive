package blackdrive.cl.clientes_service.exception;

public class SueldoInvalidoException extends RuntimeException {
    public SueldoInvalidoException(Integer sueldo) {
        super("El sueldo ingresado no es válido: " + sueldo);
    }
}