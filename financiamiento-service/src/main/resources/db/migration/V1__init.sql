CREATE TABLE IF NOT EXISTS financamiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuotas INT,
    monto_total INT,
    monto_cuota INT,
    fecha_inicio DATE,
    estado VARCHAR(255),
    cliente_id BIGINT,
    vehiculo_id BIGINT
    );