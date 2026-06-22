# BlackDrive - Sistema de Gestión de Concesionaria

Plataforma de microservicios para la gestión integral de una concesionaria de vehículos: clientes, vehículos, pagos, facturas, inventario, pedidos, servicios, sucursales, vendedores y financiamiento.

## Integrante

- Felipe Castillo

## Tecnologías

- Java 21
- Spring Boot 3.x
- Spring Cloud (Eureka, API Gateway, OpenFeign, Config Server)
- MySQL 8
- Flyway (migraciones SQL)
- Swagger / OpenAPI (springdoc-openapi)
- Docker + Docker Compose
- JUnit 5 + Mockito

## Microservicios implementados

| Microservicio | Puerto local | Ruta Gateway |
|---|---|---|
| eureka-server | 8761 | — |
| config-server | 8888 | — |
| api-gateway | 8080 | — |
| clientes-service | 8091 | `/api/v6/clientes/**` |
| pagos-service | 8098 | `/api/v6/pagos/**` |
| vehiculos-service | 8093 | `/api/v6/vehiculos/**` |
| pedidos-service | 8092 | `/api/v6/pedidos/**` |
| sucursales-service | 8094 | `/api/v6/sucursales/**` |
| vendedores-service | 8095 | `/api/v6/vendedores/**` |
| inventario-service | 8096 | `/api/v6/inventario/**` |
| servicios-service | 8097 | `/api/v6/servicios/**` |
| facturas-service | 8099 | `/api/v6/facturas/**` |
| financiamiento-service | 8100 | `/api/v6/financiamiento/**` |

## Documentación Swagger

Disponible por microservicio una vez levantado el sistema:

- Clientes: `http://localhost:8091/swagger-ui/index.html`
- Pagos: `http://localhost:8098/swagger-ui/index.html`

También accesible vía Gateway: `http://localhost:8080/swagger-ui/index.html`

# BlackDrive

## Iniciar con Docker (forma correcta)

```bash
# Primera vez o si hubo cambios en el código
docker compose up --build

# Sin cambios (más rápido, usa imágenes ya construidas)
docker compose up

# Apagar todo
docker compose down

# Apagar y borrar la base de datos (reinicio limpio)
docker compose down -v
```

Verificar que todo levantó: `http://localhost:8761`
Deben aparecer todos los microservicios registrados en Eureka.

## Swagger

- Clientes: `http://localhost:8091/swagger-ui/index.html`
- Pagos: `http://localhost:8098/swagger-ui/index.html`

## Iniciar sin Docker (desde IntelliJ)

Orden obligatorio, esperar que cada uno cargue antes del siguiente:

1. `eureka-server`
2. `config-server`
3. Los microservicios de dominio (clientes, pagos, etc.)
4. `api-gateway`
