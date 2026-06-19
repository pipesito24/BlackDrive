CREATE TABLE IF NOT EXISTS inventario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255),
    categoria VARCHAR(255),
    stock INT,
    precio_unitario INT,
    vehiculo_id BIGINT
    );