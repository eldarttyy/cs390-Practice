# TODO List Example

Minimal example of a SpringBoot based server application
that uses MongoDB to support a TODO list.

## Prerequisites

Install Docker desktop: https://www.docker.com/products/docker-desktop/

## Database

To start the database in a Docker container, first make sure that Docker desktop is 
running.  Then run the following command to start a Docker container that contains
a MongoDB instance:

`docker compose up -d`

To stop the database:

`docker compose down`

## Server

To start server:

* `./gradlew bootRun`
* Use the green arrow in IntelliJ near the main method.
* Select Tasks -> application -> bootRun from the Gradle window in IntelliJ
