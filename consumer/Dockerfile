FROM amazoncorretto:23-alpine3.21

LABEL authors="rohdef"

COPY build/distributions/consumer.tar ./

RUN tar xf consumer.tar

ENTRYPOINT ["sh", "consumer/bin/consumer"]
