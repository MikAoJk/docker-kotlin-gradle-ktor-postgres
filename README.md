[![build main branch](https://github.com/MikAoJk/docker-kotlin-gradle-ktor-postgres/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/MikAoJk/docker-kotlin-gradle-ktor-postgres/actions/workflows/build.yml)
![GitHub issues](https://img.shields.io/github/issues-raw/MikAoJk/docker-kotlin-gradle-ktor-postgres)
![GitHub pull requests](https://img.shields.io/github/issues-pr-raw/MikAoJk/docker-kotlin-gradle-ktor-postgres)
![GitHub](https://img.shields.io/github/license/MikAoJk/docker-kotlin-gradle-ktor-postgres)

# docker-kotlin-gradle-ktor-postgres
This project is for testing development with docker, kotlin, gradle, ktor and postgreSQL

## Technologies used
* JDK 17
* Kotlin
* Gradle
* Docker
* Ktor
* Postgres
* Editorconfig

## Getting started

### Prerequisites
Make sure you have the Java JDK 17 installed
You can check which version you have installed using this command:
``` bash
java -version
```

### Running the application locally

#### Building the application
To build locally and run the integration tests you can simply run
``` bash
./gradlew clean build
```
or on windows `gradlew.bat clean build`

#### Integrasion testing the application
### Running the postgresSql db from docker compose
``` bash
docker-compose -p docker-kotlin-gradle-ktor-postgres-compose up -d
```

### Tear down the postgresSql db from docker compose
``` bash
docker-compose -p docker-kotlin-gradle-ktor-postgres-compose down
```

### Run the main class in your favoritt IDE(Intellij)
Go to src/main/kotlin/Bootstrap.kt and run it

### Testing the endpoint

#### Api doc
http://localhost:8080/swagger

For testing the endpoint ValidateDataApi
You need a tool to send a request and to inspect the repsonse
A tool you can use is Postman: https://www.postman.com/downloads/
The endpoint is currently available at http://$yourlocalhost/v1/validate when running the application locally
Set the body in the request, example: `{"data":"DATA"}` and profit

Example of a request:
`curl --location --request POST 'http://$yourlocalhost:8080/v1/validate' \
--header 'Content-Type: application/json' \
--data-raw '{"data":"DATA"}'`

Example of a response:
`{"result":"OK"}`

### Upgrading the gradle wrapper
Find the newest version of gradle here: https://gradle.org/releases/ Then run this command:

``` bash
./gradlew wrapper --gradle-version $gradleVersjon
```

Remeber to also update the gradle version in the build.gradle.kts file
``` bash
gradleVersion = "$gradleVersjon"
```

#### Creating a docker image
Creating a docker image should be as simple as
``` bash
docker build -t docker-kotlin-gradle-ktor-postgres .
```

#### Running a docker image
``` bash
docker run -d --rm -it -p 8080:8080 docker-kotlin-gradle-ktor-postgres
```

### Contribute
Want to add a feature? see [CONTRIBUTING](CONTRIBUTING.md)

### Contact

This project is maintained by [CODEOWNERS](CODEOWNERS)

Questions please create an
[issue](https://github.com/MikAoJk/docker-kotlin-gradle-ktor-postgres/issues)

