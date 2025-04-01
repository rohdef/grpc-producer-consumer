package dk.rohdef.danske_bank;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Hello, I am Service B; the consumer!");

        String host = Objects.requireNonNullElse(System.getenv("PRODUCER_HOST"), "localhost");
        int port = 50051;

        logger.info("Creating channel to {}:{}...", host, port);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();

        try {
            MessagesClient client = new MessagesClient(channel);
            CountDownLatch countDownLatch = client.messages();
            countDownLatch.await();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }

        logger.info("Channel ended, closing...");
    }
}