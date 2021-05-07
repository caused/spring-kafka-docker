# spring-kafka-docker
Spring reactive project with webflux and kafka integration

## Prerequisites to run the project:

* Maven
* Docker
* JDK 11

__All instructions here should be done in the terminal__

Before running the project execute the following command in the root folder:

```
docker-compose up -d
```

Now you have both Kafka Broker and Zookeper running. Now, navigate to project folder and execute the following command to deploy the api:

```
mvn spring-boot:run
```

With the project started we can go to terminal to test it using curl:

```
curl -d '{ "status": "INFO", "message": "WELL DONE" }' -H "Content-Type: application/json" -X POST http://localhost:8080
```

Look at both project and curl terminals to see the result of the api call with kafka publishing and consuming




