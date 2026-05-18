CREATE TABLE IF NOT EXISTS facturas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_factura VARCHAR(255),
    monto_total INT,
    iva INT,
    monto_neto INT,
    fecha_emision DATE,
    estado VARCHAR(255),
    cliente_id BIGINT,
    pago_id BIGINT
    );