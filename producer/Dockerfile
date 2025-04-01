FROM eclipse-temurin:23-jre-alpine

EXPOSE 50051
LABEL authors="rohdef"

COPY build/distributions/producer.tar ./

RUN tar xf producer.tar

ENTRYPOINT ["sh", "producer/bin/producer"]