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
    
TBD


## Project structure

- [protocol](./protocol) - the protocol definition and generated java library for the communication defined in protobuf v3
- [producer](./producer) - *Service A*, the producer of messages to be consumed. Produces a message each second
- [consumer](./consumer) - *Service B*, the consumer for the messages produced by *Service A*