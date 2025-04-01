FROM eclipse-temurin:23-jre-alpine

LABEL authors="rohdef"

COPY build/distributions/consumer.tar ./

RUN tar xf consumer.tar

ENTRYPOINT ["sh", "consumer/bin/consumer"]