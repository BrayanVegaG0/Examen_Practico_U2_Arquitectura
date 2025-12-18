# Microservicio de Gestión de Sucursales (Branch API)

Este proyecto implementa una API REST para la gestión de sucursales bancarias ("Branches") y sus respectivos feriados, desarrollado como parte del Examen Práctico de Arquitectura de Software.

## 📋 Descripción

El servicio permite realizar operaciones CRUD sobre sucursales, con reglas de negocio específicas como validación de unicidad por correo, gestión de una lista embebida de feriados y verificación de fechas festivas.

La arquitectura sigue el patrón **Layered Architecture** (Capas) y utiliza **MongoDB** como motor de persistencia.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.4+
* **Base de Datos:** MongoDB
* **Mappers:** MapStruct 1.5.5
* **Boilerplate reduction:** Lombok
* **Documentación:** SpringDoc OpenApi (Swagger UI) v2.8.0

## 🚀 Requisitos Previos

1.  **Java JDK 21** instalado.
2.  **MongoDB** ejecutándose en el puerto `27017`.

## ⚙️ Instrucciones de Ejecución

Sigue estos pasos para levantar el proyecto localmente:

### 1. Clonar o descargar el proyecto
Abre la carpeta del proyecto en tu terminal o IDE (Visual Studio Code).

### 2. Compilar el proyecto
Ejecuta el siguiente comando para limpiar, compilar y descargar dependencias:

```bash
./mvnw clean install
