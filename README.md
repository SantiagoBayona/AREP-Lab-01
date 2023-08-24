# TALLER 1: APLICACIONES DISTRIBUIDAS (HTTP, SOCKETS, HTML, JS,MAVEN, GIT)

Aplicación para consultar la información de películas de cine

### Prerrequisitos
- Java
- Maven

### Instalación

1. Clonar el repositorio

```
git clone https://github.com/SantiagoBayona/AREP-Lab-01
```

2. Dentro del directorio del proyecto lo construimos

```
mvn package
```

## Ejecución

1. Corremos el servidor

```
mvn exec:java -"Dexec.mainClass"="edu.escuelaing.HttpServer"
```

2. Ingresamos a la página mediante esta URL en un navegador

```
https://localhost:35000
```

## Pruebas

Para ejecutar las pruebas usamos el comando

```
mvn test
```

## Construido con

* Java
* Maven
* Git
* HTML y JS

## Autor

* **Santiago Bayona**
