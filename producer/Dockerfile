FROM eclipse-temurin:23-jre-alpine

EXPOSE 50051
LABEL authors="rohdef"

COPY build/distributions/producer.tar ./

# Workaround for current security issue
RUN apk update
RUN apk add libexpat=2.7.0-r0

RUN tar xf producer.tar

ENTRYPOINT ["sh", "producer/bin/producer"]