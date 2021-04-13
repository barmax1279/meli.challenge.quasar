# Challenge -- MercadoLibre
## Operation Quasar Fire

### Introduccion
Han Solo ha sido recientemente nombrado General de la Alianza
Rebelde y busca dar un gran golpe contra el Imperio Galáctico para
reavivar la llama de la resistencia.
El servicio de inteligencia rebelde ha detectado un llamado de auxilio de
una nave portacarga imperial a la deriva en un campo de asteroides. El
manifiesto de la nave es ultra clasificado, pero se rumorea que
transporta raciones y armamento para una legión entera.

### Desafio
Como jefe de comunicaciones rebelde, tu misión es crear un programa en Golang que retorne
la fuente y contenido del mensaje de auxilio. Para esto, cuentas con tres satélites que te
permitirán triangular la posición, ¡pero cuidado! el mensaje puede no llegar completo a cada
satélite debido al campo de asteroides frente a la nave.

### Solucion Planteada
Para obtener la ubicacion de la nave a conociento la ubicacion de 3 puestos fijos y pudiendo calcular
la distancia hacia cada uno de los tres puntos conocidos, se debe utilizar el metodo de Trilateration.
Ver informacion en Trilateración - [Wikipedia](https://es.wikipedia.org/wiki/Trilateraci%C3%B3n)

####Documentacion adicional
How Does Your GPS Device Know Where You Are? - Popular Maths - Nagwa - [Youtube] (https://www.youtube.com/watch?v=4fXjc9uibGM)
Trilateracion Casio9860gii - [Youtube] (https://www.youtube.com/watch?v=ktPuxq3UVX4)

### Requerimientos

El proyecto fue desarrollado utilizando los siguientes framworks:

- Java 11.0.10.hs-adpt
- Apache Maven 3.8.1

### Desarrollado

Herramientas y framworks utilizandos:

* [Sprint Boot](https://spring.io/projects/spring-boot) - Web Framework
* [Maven](https://maven.apache.org/) - Dependencies Management
* [Intellij Idea Community Edition](https://www.jetbrains.com/es-es/idea/download) - IDE

Libreria Adicional

* [la4j] org.la4j - (http://la4j.org/) -  Linear Algebra for Java

#### Instalacion

#### GIT Clone / Compilacion
```
# clone project
git clone https://github.com/barmax1279/meli.challenge.quasar.git
cd meli.challenge.quasar
mvn clean install

Unit Test
# execute unit test
mvn test 
```

Postman

[Test cases in postman: support v2.1 Collections](doc/postman/challenge.postman_collection_v2.1.json)

| Orden | Nombre                   | Metod | restultado                   |
|-------|--------------------------|-------|------------------------------|
|   1   | topsecret                | POST  | Position (x / y) & Message   |
|   2   | topsecret_badrequest     | POST  | Bad_Request                  |
|       |                          |       |                              |
|   3   | topsecret_split_kenobi   | POST  | Ok                           |
|   4   | topsecret_split_skywalker| POST  | Ok                           |
|   5   | topsecret_split_sato     | POST  | Ok                           |
|   6   | topsecret_split_GET      | GET   | Position (x / y) & Message   |
|       |                          |       |                              |
|   7   | topsecret_split_GET      | GET   | null                         |


# Ambientes

| Env   | url                   |
|-------|-----------------------|
| dev   | http://127.0.0.1:8080 |
| prod  | http://18.191.28.229/ |

