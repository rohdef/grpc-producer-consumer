FROM eclipse-temurin:23-jre-alpine

LABEL authors="rohdef"

COPY build/distributions/consumer.tar ./

# Workaround for current security issue
RUN apk update
RUN apk add libexpat=2.7.0-r0

RUN tar xf consumer.tar

ENTRYPOINT ["sh", "consumer/bin/consumer"]