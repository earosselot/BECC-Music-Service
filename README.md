
# Music Service Backend Challenge

This is a service challenge made for an interview with Plugsurfing.

The Api is an aggregator that gathers data from different APIs and put it all together for the consumer.

## Build and Run

### With gradle and java for local development

Run this command to build the application:
```bash
./gradlew build
```

Run this command to run the application:
```bash
./gradlew bootRun
```

#### Swagger Documentation

When executed locally there is a swagger documentation available at:
http://localhost:8080/musify/swagger-ui/index.html

### With Docker for production

Run those commands to create the image and to create and run the container:
```bash
docker build -t musify .
docker run --name musifyApp -p 8080:8080 musify
```

Once the container has been created with `docker run` it can be started and ended with:
```bash
docker start musifyApp
docker stop musifyApp
```

### Test

Acceptance and Unit test can be run with the following commands:
```bash
./gradlew test --tests '*AcceptanceTest'
./gradlew test --tests '*UniTest'
```


## Toolset

- Java 17
- Gradle 
- Spring Boot 3.2.0
- Spring Boot Devtools
- Spring OpenApi - Swagger
- Junit
- Mockito
- Docker

## Endpoints

- GET /musify/music-artist/details/{mbid}

This endpoint provides information from an artist given a mbid (Music Brainz ID):

```json
{
  "mbid": "string",
  "name": "string",
  "gender": "string",
  "country": "string",
  "disambiguation": "string",
  "description": "string",
  "albums": [
    {
      "id": "string",
      "title": "string",
      "imageUrl": "string"
    }
  ]
}
```

Examples of artists and mbid are:
  - Megadeath (a9044915-8be3-4c7e-b11f-9e2d2ea0a91e)
  - AC/DC (66c662b6-6e2f-4930-8610-912e24c63ed1)
  - Audioslave (020bfbb4-05c3-4c86-b372-17825c262094)
  - Pink Floyd (83d91898-7763-47d7-b03b-b92132375c47)
  - Charly Garcia (dd999ced-1dd4-44b8-ab67-ee5ec23e52b7)
  - Divididos (fdae985a-b8e1-4a2b-919d-8ee12cec403e)
  - Patricio Rey y sus Redonditos de Ricota (956301d0-4b3c-4ffc-9898-b39305d25112)

```bash
curl -X GET "http://localhost:8080/musify/music-artist/details/a9044915-8be3-4c7e-b11f-9e2d2ea0a91e"
curl -X GET "http://localhost:8080/musify/music-artist/details/66c662b6-6e2f-4930-8610-912e24c63ed1"
curl -X GET "http://localhost:8080/musify/music-artist/details/020bfbb4-05c3-4c86-b372-17825c262094"
curl -X GET "http://localhost:8080/musify/music-artist/details/83d91898-7763-47d7-b03b-b92132375c47"
```

## Design decisions

### Always response data when possible

The service requests information from different sources, but all the request depends ultimately on data gathered from Music Brainz. 
The dependency is as follows:
  1. Music Brainz (MB) -> Provides almost all the artist Data
     1. Wikidata (WD) -> Depends on MB -> Provides aritst title for WP
        1. Wikipedia (WK) -> Depends en WD -> Provides the artist description
     2. Cover Art Archive (CAA) -> Depends on MB -> Provides the link to album cover for each album

If the query to Music Brainz provides data, then the service will at least respond with this data, even when all the other 3rd party API requests fail.
The fields that have not been completed won't be in the response. 
For example, if the service fails to gather the link to an album cover, then the album will have this form:
```json
{
  "id": "string",
  "title": "string"
}
```

### Asynchronous request to 3rd Apis to improve response time

For the way we have to aggregate all the information needed to build the response several calls to 3rd party APIs will be made.
If this call were done synchronously there will be a chain of calls that could take a long time to complete.
To address this issue the application parallelize the requests to 3rd party APIs using the asynchronous tool `CompletableFuture` provided by java.
With this parallelization the response times are significantly reduced.

### Cache to improve response times

The application implements caching for all the queries to 3rd party APIs.
As the service provides information that will probably no change over time for most cases, 
we can confidently save the responses of all the services we depend on to gain a considerable performance improvement.
The cache implementation used is the Generic Cache that comes along with Spring Boot through the use of the annotation `@Cacheable("cache-name")`.
As the responses from 3rd party APIs might contain a big amount of data not relevant to our application, only the useful data will be cached to avoid running into memory issues.
If the application have horizontal scaling, then a distributed cache would be a good next step to further improve the response times.

### Availability

The availability of the application will be provided via horizontal scaling.
As it is a containerized application is ready to operate and easy to replicate as becomes necessary.
