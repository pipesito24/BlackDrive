CREATE TABLE IF NOT EXISTS pagos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto INT,
    fecha DATE,
    metodo_pago VARCHAR(255),
    cliente_id BIGINT
    );