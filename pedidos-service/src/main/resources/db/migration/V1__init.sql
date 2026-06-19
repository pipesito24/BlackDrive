CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255),
    cantidad INT,
    precio_unitario INT,
    fecha_pedido DATE,
    estado VARCHAR(255),
    cliente_id BIGINT
    );