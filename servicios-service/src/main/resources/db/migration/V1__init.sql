CREATE TABLE IF NOT EXISTS servicios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_servicio VARCHAR(255),
    descripcion VARCHAR(255),
    costo INT,
    fecha_servicio DATE,
    estado VARCHAR(255),
    vehiculo_id BIGINT
    );