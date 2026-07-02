# BlackDrive - Sistema de Gestión de Concesionaria

BlackDrive es una plataforma basada en arquitectura de microservicios para la gestión integral de una concesionaria de vehículos. El sistema permite administrar clientes, vehículos, pagos, facturas, inventario, pedidos, servicios, sucursales, vendedores y financiamiento.

El acceso funcional a los microservicios se centraliza mediante un **API Gateway**, utilizando **Eureka Server** para descubrimiento de servicios y **Config Server** para configuración centralizada.

---

## Integrante

- Felipe Castillo

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3.x
- Spring Cloud
  - Eureka Server
  - API Gateway
  - OpenFeign
  - Config Server
- MySQL 8
- JPA + Hibernate
- Flyway para migraciones SQL
- Swagger / OpenAPI con springdoc-openapi
- JUnit 5 + Mockito
- Docker + Docker Compose

---

## Arquitectura del sistema

El proyecto está compuesto por servicios de infraestructura y microservicios de dominio.

### Servicios de infraestructura

| Servicio | Responsabilidad | Puerto |
|---|---|---|
| `eureka-server` | Registro y descubrimiento de microservicios | `8761` |
| `config-server` | Configuración centralizada de los servicios | `8888` |
| `api-gateway` | Punto de entrada único al sistema | `8080` |

### Microservicios de dominio

Los microservicios de dominio utilizan **puertos dinámicos**, por lo que no se accede directamente a ellos en ejecución local. El acceso debe realizarse desde el API Gateway.

| Microservicio | Responsabilidad | Ruta Gateway |
|---|---|---|
| `clientes-service` | Gestión de clientes | `/api/v6/clientes/**` |
| `vehiculos-service` | Gestión de vehículos y relación con clientes | `/api/v6/vehiculos/**` |
| `pedidos-service` | Gestión de pedidos | `/api/v6/pedidos/**` |
| `pagos-service` | Gestión de pagos y relación con clientes | `/api/v6/pagos/**` |
| `facturas-service` | Gestión de facturas | `/api/v6/facturas/**` |
| `sucursales-service` | Gestión de sucursales | `/api/v6/sucursales/**` |
| `vendedores-service` | Gestión de vendedores y sucursales | `/api/v6/vendedores/**` |
| `inventario-service` | Gestión de inventario de vehículos | `/api/v6/inventario/**` |
| `servicios-service` | Gestión de servicios asociados a vehículos | `/api/v6/servicios/**` |
| `financiamiento-service` | Gestión de financiamiento de vehículos | `/api/v6/financiamiento/**` |

---

## Configuración de puertos

Los microservicios de dominio están configurados con puertos dinámicos mediante Config Server:

```yml
server:
  port: 0
```

Esto permite evitar conflictos de puertos en ejecución local.  
El descubrimiento de cada microservicio se realiza mediante Eureka.

El API Gateway mantiene un puerto fijo configurable:

```yml
server:
  port: ${GATEWAY_PORT:8080}
```

Si el puerto `8080` está ocupado, se puede cambiar desde PowerShell:

```powershell
$env:GATEWAY_PORT="9090"
```

Luego se levanta nuevamente el `api-gateway`.

---

## Orden de ejecución local

La ejecución local es la forma principal validada para trabajar el proyecto.

Ejecutar los servicios en este orden desde IntelliJ IDEA:

1. `eureka-server`
2. `config-server`
3. Microservicios de dominio:
   - `clientes-service`
   - `vehiculos-service`
   - `sucursales-service`
   - `vendedores-service`
   - `inventario-service`
   - `servicios-service`
   - `pedidos-service`
   - `pagos-service`
   - `facturas-service`
   - `financiamiento-service`
4. `api-gateway`

Después de iniciar Eureka, verificar:

```text
http://localhost:8761
```

Deben aparecer registrados los servicios levantados.

---

## Acceso a la API mediante Gateway

Todas las pruebas funcionales deben realizarse desde el API Gateway.

URL base:

```text
http://localhost:8080
```

Ejemplos:

```text
GET http://localhost:8080/api/v6/clientes
GET http://localhost:8080/api/v6/vehiculos
GET http://localhost:8080/api/v6/pagos
GET http://localhost:8080/api/v6/facturas
```

Si el Gateway se ejecuta en otro puerto, por ejemplo `9090`, se debe reemplazar el puerto:

```text
http://localhost:9090/api/v6/clientes
```

---

## Rutas principales por Gateway

| Operación | Ruta |
|---|---|
| Clientes | `http://localhost:8080/api/v6/clientes` |
| Vehículos | `http://localhost:8080/api/v6/vehiculos` |
| Sucursales | `http://localhost:8080/api/v6/sucursales` |
| Vendedores | `http://localhost:8080/api/v6/vendedores` |
| Inventario | `http://localhost:8080/api/v6/inventario` |
| Servicios | `http://localhost:8080/api/v6/servicios` |
| Pedidos | `http://localhost:8080/api/v6/pedidos` |
| Pagos | `http://localhost:8080/api/v6/pagos` |
| Facturas | `http://localhost:8080/api/v6/facturas` |
| Financiamiento | `http://localhost:8080/api/v6/financiamiento` |

---

## Swagger / OpenAPI

La documentación Swagger está centralizada desde el API Gateway:

```text
http://localhost:8080/swagger-ui.html
```

También puede abrirse como:

```text
http://localhost:8080/swagger-ui/index.html
```

Actualmente la documentación Swagger está configurada para:

- `clientes-service`
- `pagos-service`

Desde la interfaz Swagger del Gateway se puede seleccionar el microservicio documentado.

---

## Base de datos

Cada microservicio trabaja con una base de datos MySQL independiente.

Bases utilizadas:

| Microservicio | Base de datos |
|---|---|
| `clientes-service` | `bd_clientes` |
| `vehiculos-service` | `bd_vehiculos` |
| `pedidos-service` | `bd_pedidos` |
| `pagos-service` | `bd_pagos` |
| `facturas-service` | `bd_facturas` |
| `sucursales-service` | `bd_sucursales` |
| `vendedores-service` | `bd_vendedores` |
| `inventario-service` | `bd_inventario` |
| `servicios-service` | `bd_servicios` |
| `financiamiento-service` | `bd_financiamiento` |

Configuración por defecto:

```text
Usuario: root
Contraseña: vacía
```

Si MySQL utiliza contraseña, configurar la variable de entorno antes de levantar los servicios:

```powershell
$env:SPRING_DATASOURCE_PASSWORD="TU_CLAVE"
```

---

## Migraciones de base de datos

El proyecto utiliza Flyway para inicializar las bases de datos mediante scripts SQL ubicados en:

```text
src/main/resources/db/migration
```

Cada microservicio mantiene sus propias migraciones.

---

## Docker

El proyecto incluye archivos Docker y `docker-compose.yml`.

Comandos principales:

```bash
docker compose up --build
docker compose up
docker compose down
docker compose down -v
```

Sin embargo, la ejecución principal validada para el trabajo local es mediante IntelliJ IDEA, debido a posibles restricciones de red o problemas de acceso en el entorno institucional.

---

## Pruebas unitarias

Las pruebas unitarias están implementadas principalmente en:

- `clientes-service`
- `pagos-service`

Incluyen pruebas con:

- JUnit 5
- Mockito
- Pruebas de capa Service
- Pruebas de Controller
- Validación de casos correctos y casos de error

---

## Consideraciones técnicas

- El proyecto mantiene una separación por capas bajo el patrón CSR:
  - Controller
  - Service
  - Repository / Model
- El acceso externo se centraliza mediante API Gateway.
- Eureka permite registrar microservicios aunque usen puertos dinámicos.
- Config Server centraliza la configuración mediante archivos YAML.
- Los microservicios se comunican usando OpenFeign.
- Las respuestas REST se mantienen en formato JSON.
- Swagger se expone mediante el Gateway para los servicios documentados.
- Docker queda disponible como alternativa, pero la ejecución local es la forma principal de validación.

---

## Comandos Git útiles

Ver estado del repositorio:

```bash
git status
```

Agregar cambios:

```bash
git add .
```

Crear commit:

```bash
git commit -m "Mensaje tecnico del cambio"
```

Subir cambios:

```bash
git push origin master
```
