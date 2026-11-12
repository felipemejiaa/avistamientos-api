# Taller 5: Clean Architecture y Arquitectura Hexagonal

**Felipe Mejía**
Ingeniería de Software 2 — Universidad El Bosque
Profesor: Jonathan Briceño
Noviembre de 2026

---

## Tabla de Contenido

- [Parte 1: Investigación](#parte-1-investigación)
  - [Pregunta 1: Arquitectura Hexagonal (Cockburn, 2005)](#pregunta-1-arquitectura-hexagonal-cockburn-2005)
  - [Pregunta 2: Clean Architecture (Martin, 2012)](#pregunta-2-clean-architecture-martin-2012)
  - [Pregunta 3: Inversión de dependencias](#pregunta-3-inversión-de-dependencias)
  - [Pregunta 4: Comparación de las tres arquitecturas](#pregunta-4-comparación-de-las-tres-arquitecturas)
  - [Pregunta 5: ¿Se justifica en mi API?](#pregunta-5-se-justifica-en-mi-api)
  - [Referencias](#referencias)
- [Parte 2: Aplicación](#parte-2-aplicación)
  - [Repositorio](#repositorio)
  - [Arquitectura elegida](#arquitectura-elegida)
  - [¿Qué fue lo más difícil de separar y por qué?](#qué-fue-lo-más-difícil-de-separar-y-por-qué)

---

## Parte 1: Investigación

### Pregunta 1: Arquitectura Hexagonal (Cockburn, 2005)

El problema que Cockburn identifica es que no se podía probar la aplicación sin necesitar la interfaz de usuario o la base de datos. La lógica de negocio se "filtraba" hacia esas partes, así que si querías probar algo o reutilizar la app desde otro programa, no podías hacerlo fácilmente (Cockburn, 2005).

Un puerto es un punto de entrada o salida de la aplicación, sin importar qué o cómo se conecta; es simplemente un punto de acceso definido por lo que se quiere hacer. Un adaptador es una especie de intermediario entre el puerto y lo que se va a conectar: este sí se preocupa por la tecnología que se está usando, para traducirlo y que el puerto lo entienda (Cockburn, 2005).

La simetría existe porque el modelo de "arriba y abajo" con el que se solía representar la arquitectura no es cierto. Tanto la interfaz de usuario como la base de datos están "afuera" del hexágono; realmente solo importa si algo está dentro o fuera del sistema (Cockburn, 2005).

### Pregunta 2: Clean Architecture (Martin, 2012)

En el círculo más interno van las entidades, que son las reglas de negocio. Luego los casos de uso, que son las reglas de la aplicación. Después los adaptadores de interfaz, como controladores y gateways. Y en el círculo de afuera van los drivers y frameworks, como la base de datos o el framework web (Martin, 2012).

La regla de dependencia dice que las dependencias solo pueden apuntar hacia adentro. Eso quiere decir que un círculo interno no puede conocer ni usar nada de uno externo: ni sus clases, ni sus funciones, ni sus formatos de datos. Lo de adentro no sabe que lo de afuera existe (Martin, 2012).

### Pregunta 3: Inversión de dependencias

Se resuelve con inversión de dependencias: el caso de uso define una interfaz adentro que representa lo que necesita (por ejemplo, guardar datos), y la implementación que habla con la base de datos real va en la capa de afuera (Martin, 2012). En mi API, por ejemplo, el caso de uso "guardar avistamiento" no llamaría directamente al repositorio JPA, sino a una interfaz como RepositorioDeAvistamientos. La implementación con JPA iría en la capa externa. Si mañana quiero cambiar de base de datos, no toco la lógica de negocio.

### Pregunta 4: Comparación de las tres arquitecturas

Las tres tienen en común que la lógica de negocio va en el centro, es independiente del framework, la interfaz y la base de datos, se puede probar sin esas cosas, y las dependencias apuntan hacia adentro (Cockburn, 2005; Palermo, 2008; Martin, 2012).

Lo que cambia es el vocabulario y en qué hace énfasis cada una: la hexagonal habla de puertos y adaptadores, y lo que más le importa es la simetría entre la UI y la BD (Cockburn, 2005). La de cebolla usa capas concéntricas de dominio, servicios de dominio y servicios de aplicación, y critica que en la arquitectura tradicional todo depende de la base de datos (Palermo, 2008). Clean Architecture también usa capas concéntricas pero las llama entidades, casos de uso, adaptadores de interfaz y frameworks, y pone todo el foco en la regla de dependencia estricta (Martin, 2012).

### Pregunta 5: ¿Se justifica en mi API?

Un costo es que la API es pequeña, entonces lo que hoy se resuelve con pocas clases pasa a tener más archivos y carpetas por la separación en capas. Para el tamaño que tiene, es más código del que necesita.

Un beneficio es que se puede cambiar de base de datos o probar la lógica sin levantar el framework. De hecho, en esta API ya apliqué inversión de dependencias con Reloj y RelojDelSistema, y gracias a eso pude hacer pruebas unitarias con un RelojFijo sin tocar el reloj real.

Mi postura es que sí se justifica: la separación obliga a que cada componente tenga pocas responsabilidades, lo que hace más fácil escalar el código y cumplir con bajo acoplamiento y alta cohesión. Además, en un contexto académico el beneficio principal es aprender a separar responsabilidades, y ese aprendizaje se transfiere a proyectos más grandes donde es indispensable.

### Referencias

- Cockburn, A. (2005). Hexagonal Architecture. https://alistair.cockburn.us/hexagonal-architecture/
- Martin, R. C. (2012). The Clean Architecture. https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- Palermo, J. (2008). The Onion Architecture. https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/

---

## Parte 2: Aplicación

### Repositorio

https://github.com/felipemejiaa/avistamientos-api

Rama: `main`

### Arquitectura elegida

Elegí la Arquitectura Hexagonal (Ports and Adapters) de Cockburn (2005). Es la más adecuada para el tamaño de esta API: más sencilla de implementar y mantener que Clean Architecture o la de cebolla, y su vocabulario de puertos y adaptadores se mapea directamente a los componentes de la aplicación.

### ¿Qué fue lo más difícil de separar y por qué?

Lo más difícil fue separar la entidad Avistamiento. Antes era una sola clase que hacía de todo: era el modelo de dominio y al mismo tiempo la entidad de JPA con las anotaciones `@Entity`, `@Table`, `@Id` y `@GeneratedValue`. Eso significaba que el dominio dependía directamente de la base de datos, que es justo lo que la arquitectura hexagonal busca evitar.

Para separarla tuve que crear dos clases: un Avistamiento limpio en el dominio (sin nada de JPA) y una AvistamientoEntidad en la capa de infraestructura (con todas las anotaciones). Además, fue necesario crear un adaptador que convierte entre las dos: cada vez que se guarda o se consulta un avistamiento, el adaptador traduce del dominio a la entidad y viceversa.

El otro punto complicado fue actualizar las pruebas de integración, porque usaban directamente el repositorio de JPA para preparar los datos de prueba. Había que cambiar los imports y usar la entidad JPA en vez del modelo de dominio para las operaciones de base de datos dentro de los tests.
