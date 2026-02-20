# Conversor de Monedas (Java) — ExchangeRate API

Proyecto en Java 17 que convierte monedas usando tasas reales consumidas desde la API de ExchangeRate.
Incluye menú interactivo en consola, validaciones de entrada, consumo HTTP y procesamiento de JSON con Gson.

---

## ✅ Funcionalidades

* Menú interactivo en consola (loop `while`)
* Opciones de conversión predefinidas + opción manual
* Validación de opción y cantidad ingresada
* Consumo de API real (ExchangeRate API) con `HttpClient` (Java 17)
* Parseo de JSON con Gson a un modelo (`RespuestaApi`)
* Cálculo de conversión mediante servicio (`ConversorService`)

---

## 🧱 Tecnologías

* Java 17
* HttpClient / HttpRequest / HttpResponse
* Gson (gson-2.10.1.jar agregado como librería)
* ExchangeRate API (endpoint `/latest/{BASE}`)

---

## 📦 Estructura del proyecto
```
src/
├─ principal/
│ └─ Principal.java
├─ menu/
│ └─ Menu.java
├─ apiclient/
│ └─ ApiCliente.java
├─ modelo/
│ └─ RespuestaApi.java
└─ service/
└─ ConversorService.java
```

---

## ▶️ Cómo ejecutar

1. Abre el proyecto en **IntelliJ IDEA** (o tu IDE preferido).
2. Asegúrate de estar usando **Java 17**.
3. Agrega `gson-2.10.1.jar` como librería externa:
   - `File > Project Structure > Libraries`
4. Coloca tu **API KEY** en:

`src/principal/Principal.java`


String apiKey = "TU_API_KEY_AQUI";

Ejecuta Principal.java.

## Ejemplo de uso

Al ejecutar el programa, verás el menú principal.

Pasos:

* Selecciona una opción (1–7).
* Ingresa la cantidad a convertir.
* El programa mostrará:
* Moneda base
* Moneda objetivo
* Tasa de cambio
* Cantidad ingresada
* Resultado convertido (formateado)
