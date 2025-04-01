# Danske Bank

## Building

```bash
./gradlew clean build
docker build -t ghcr.io/rohdef/danske-bank-producer:latest ./producer
docker build -t ghcr.io/rohdef/danske-bank-consumer:latest ./consumer
```


## Pulling docker images from GHCR

```bash
docker pull ghcr.io/rohdef/danske-bank-producer:latest
docker pull ghcr.io/rohdef/danske-bank-consumer:latest
```

## Running

### By hand

```bash
docker network create danske_bank_rohdef
docker run --rm --name producer --net danske_bank_rohdef danske-bank-producer:latest
docker run --rm --name consumer --net danske_bank_rohdef -e PRODUCER_HOST=producer docker pull ghcr.io/rohdef/danske-bank-consumer:latest
```

Remember to remove the network after use by running:

```bash
docker network rm danske_bank_rohdef
```


### Docker compose
    
```bash
docker compose up
```


## Project structure

- [protocol](./protocol) - the protocol definition and generated java library for the communication defined in protobuf v3
- [producer](./producer) - *Service A*, the producer of messages to be consumed. Produces a message each second
- [consumer](./consumer) - *Service B*, the consumer for the messages produced by *Service A*

## Considerations

Chose to go with *gRPC* and *Flow*, since I haven't used them before, and I wanted to learn

Some of the configurations are kept rather minimal, e.g, there's no configuration for the port.
This in part is due to the solution being designed for containers.
Port configuration in that case is only relevant for local development (ports can easily be remapped per need in containers).

This solution simulates a large part of an CI/CD pipeline, however omitting the CD part.
The setup runs through a simple security scan - and allowed me to show off a private GitHub action for building and scanning docker containers.

Currently there's no fault tolerance apart from gRPC offers out of the box.
This could include retry logic with a backoff, having data caps etc.