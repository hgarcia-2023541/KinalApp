# KinalApp

API REST desarrollada con Spring Boot para la gestión de clientes, usuarios, productos, ventas y detalle de ventas.

## Tecnologías Utilizadas

* **Java 21**
* **Spring Boot 4.0.2**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema Gestor de Base de Datos)
* **Spring Data JPA / Hibernate**

## Requisitos Previos

Antes de ejecutar la aplicación, asegúrese de tener instalado:

* JDK 21 o superior
* Maven 3.8 o superior
* MySQL Server activo en el puerto `3306`

## Configuración de la Base de Datos

La aplicación crea la base de datos automáticamente si no existe. Solo asegúrese de que MySQL esté corriendo y actualice las credenciales en el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dbClientes_in5am?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=SU_CONTRASEÑA
```

## Instalación y Ejecución

1. Clonar el repositorio:
```bash
git clone https://github.com/hgarcia-2023541/KinalApp.git
```

2. Ingresar a la carpeta del proyecto:
```bash
cd KinalApp
```

3. Compilar el proyecto con Maven:
```bash
mvn clean install
```

4. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8081`

## Endpoints Disponibles

| Entidad       | Método | Endpoint                    |
|---------------|--------|-----------------------------|
| Cliente       | GET    | /api/clientes               |
| Cliente       | POST   | /api/clientes               |
| Cliente       | PUT    | /api/clientes/{id}          |
| Cliente       | DELETE | /api/clientes/{id}          |
| Usuario       | GET    | /api/usuarios               |
| Usuario       | POST   | /api/usuarios               |
| Usuario       | PUT    | /api/usuarios/{id}          |
| Usuario       | DELETE | /api/usuarios/{id}          |
| Producto      | GET    | /api/productos              |
| Producto      | POST   | /api/productos              |
| Producto      | PUT    | /api/productos/{id}         |
| Producto      | DELETE | /api/productos/{id}         |
| Venta         | GET    | /api/ventas                 |
| Venta         | POST   | /api/ventas                 |
| Venta         | PUT    | /api/ventas/{id}            |
| Venta         | DELETE | /api/ventas/{id}            |
| DetalleVenta  | GET    | /api/detalle-ventas         |
| DetalleVenta  | POST   | /api/detalle-ventas         |
| DetalleVenta  | PUT    | /api/detalle-ventas/{id}    |
| DetalleVenta  | DELETE | /api/detalle-ventas/{id}    |

## Estructura del Proyecto

```
src/
└── main/
    ├── java/com/herbertgarcia/kinalapp/
    │   ├── controller/
    │   ├── entity/
    │   ├── repository/
    │   └── service/
    └── resources/
        └── application.properties
```

## Autor

**Herbert García** — `hgarcia-2023541@kinal.edu.gt`
