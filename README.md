# docker-kotlin-gradle-ktor-postgres
[![build main branch](https://github.com/MikAoJk/docker-kotlin-gradle-ktor-postgres/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/MikAoJk/docker-kotlin-gradle-ktor-postgres/actions/workflows/build.yml)
## Technologies used
* JDK 17
* Kotlin
* Gradle
* Docker
* Ktor
* Postgres

## Getting started
## Running application locally

### Building the application
#### Compile and package application
To build locally and run the integration tests you can simply run ./gradlew clean build or on windows gradlew.bat clean build

#### Creating a docker image
Creating a docker image should be as simple as `docker build -t docker-kotlin-gradle-ktor-postgres .`

#### Running a docker image
`docker run -d --rm -it -p 8080:8080 docker-kotlin-gradle-ktor-postgres`
on linux: http://0.0.0.0:8080
on osx: http://0.0.0.0:8080
on windows : http://127.0.0.1:8080

#### Local testing
## Running the postgresSql db from docker compose
docker-compose -p docker-kotlin-gradle-ktor-postgres-compose up -d

## run the main class in your favoritt IDE(Intellij)
Go to src/main/kotlin/Bootstrap.kt and run it

## Testing the endpoint
For testing the endpoint ValidateDataApi
You need a tool to send a request and to inspect the repsonse
A tool you can use is Postman: https://www.postman.com/downloads/
The endpoint is currently available at http://$yourlocalhost/v1/validate when running the application locally 
Set the body in the request, example: `{"data":"DATA"}` and profit

Example of a request:
`curl --location --request POST 'http://127.0.0.1:8080/v1/validate' \
--header 'Content-Type: application/json' \
--data-raw '{"data":"DATA"}'`

Example of a response:
`{"result":"OK"}`

### Tear down the postgresSql db from docker compose
docker-compose -p docker-kotlin-gradle-ktor-postgres-compose down
