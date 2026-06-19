package blackdrive.cl.clientes_service.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(Long id) {
        super("No existe un cliente con id: " + id);
    }

}
