FROM amazoncorretto:23-alpine3.21

EXPOSE 50051
LABEL authors="rohdef"

COPY build/distributions/producer.tar ./

RUN tar xf producer.tar

ENTRYPOINT ["sh", "producer/bin/producer"]