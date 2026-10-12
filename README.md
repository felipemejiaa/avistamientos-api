# API REST - Registro de avistamientos de aves

Trabajo en clase ISW-2, Sesión 17. Autor: Felipe Mejía.

API REST para registrar avistamientos de aves, hecha con Java, Spring Boot y base de datos H2 embebida.

## Requisitos

- JDK 25 o superior (`java -version` para verificar).
- No hace falta instalar Maven ni ninguna base de datos: el proyecto trae el Maven Wrapper y H2 corre embebida.

## Cómo ejecutar

```bash
git clone <URL-DE-TU-REPO>
cd avistamientos
```

Linux / macOS:
```bash
./mvnw spring-boot:run
```

Windows:
```
mvnw.cmd spring-boot:run
```

La API queda en `http://localhost:8080`. La base de datos se crea sola en la carpeta `data/`.

Consola de H2 (opcional): `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/avistamientos`
- Usuario: `user`
- Contraseña: (vacía)

## Modelo

| Campo | Tipo | Descripción |
|---|---|---|
| id | número | Lo asigna el sistema |
| especie | texto | Obligatorio |
| lugar | texto | Obligatorio |
| fecha | texto AAAA-MM-DD | Obligatorio |
| observador | texto | Obligatorio |

## Endpoints

| Método | Ruta | Respuesta |
|---|---|---|
| GET | /avistamientos | 200 con la lista |
| GET | /avistamientos/{id} | 200 o 404 |
| POST | /avistamientos | 201 o 400 si faltan datos |
| PUT | /avistamientos/{id} | 200, 404 o 400 |
| DELETE | /avistamientos/{id} | 204 o 404 |

## Ejemplos con curl

> Los ejemplos usan sintaxis de Linux/macOS/Git Bash. En CMD de Windows, cambia las comillas simples por dobles y escapa las internas con `\"`.

Listar:
```bash
curl -i http://localhost:8080/avistamientos
```

Ver uno:
```bash
curl -i http://localhost:8080/avistamientos/1
```

Registrar:
```bash
curl -i -X POST http://localhost:8080/avistamientos \
  -H "Content-Type: application/json" \
  -d '{"especie":"colibri","lugar":"Bogota","fecha":"2026-10-10","observador":"Felipe"}'
```

Actualizar:
```bash
curl -i -X PUT http://localhost:8080/avistamientos/1 \
  -H "Content-Type: application/json" \
  -d '{"especie":"condor","lugar":"Nevado del Ruiz","fecha":"2026-10-11","observador":"Felipe"}'
```

Eliminar:
```bash
curl -i -X DELETE http://localhost:8080/avistamientos/1
```

## Uso de IA

Usé **Claude** (Anthropic), modelo **Claude Opus 5.5**, durante todo el desarrollo:

- **Configuración:** me guió para crear el proyecto en Spring Initializr, configurar H2 en `application.properties` y resolver errores de descarga de dependencias de Maven.
- **Entidad y repositorio:** escribí una primera versión de la entidad con sus indicaciones. La versión final la adaptó Claude.
- **Controlador:** lo desarrollé en conjunto con Claude. Escribí las primeras versiones de los endpoints a partir de sus pistas y correcciones, y luego ajustamos la versión final a un estilo más sencillo.

Todo el código fue revisado, probado y comprendido por mí antes de publicarse.