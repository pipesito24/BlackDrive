package blackdrive.cl.clientes_service.dto;

public class RespuestaOperacion {

    private String mensaje;
    private String servicio;
    private String recurso;

    public RespuestaOperacion(String mensaje, String servicio, String recurso) {
        this.mensaje = mensaje;
        this.servicio = servicio;
        this.recurso = recurso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getServicio() {
        return servicio;
    }

    public String getRecurso() {
        return recurso;
    }
}
