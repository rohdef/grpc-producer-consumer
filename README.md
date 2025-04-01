# Danske Bank

## Building

```bash
./gradlew clean build
docker build -t producer:latest ./producer
docker build -t consumer:latest ./consumer
```


## Pulling docker images from GHCR
TBD


## Running

### By hand

```bash
docker network create danske_bank_rohdef
docker run --rm --name producer --net danske_bank_rohdef producer:latest
docker run --rm --name consumer --net danske_bank_rohdef -e PRODUCER_HOST=producer consumer:latest
```

Remember to remove the network after use by running:

```bash
docker network rm danske_bank_rohdef
```


### Docker compose

TBD


## Project structure

- [protocol](./protocol) - the protocol definition and generated java library for the communication defined in protobuf v3
- [producer](./producer) - *Service A*, the producer of messages to be consumed. Produces a message each second
- [consumer](./consumer) - *Service B*, the consumer for the messages produced by *Service A*