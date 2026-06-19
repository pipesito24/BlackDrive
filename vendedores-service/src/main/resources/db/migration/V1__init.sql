CREATE TABLE IF NOT EXISTS vendedores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255),
    telefono VARCHAR(255),
    comision DOUBLE,
    sucursal_id BIGINT
    );