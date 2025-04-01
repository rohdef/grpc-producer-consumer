package dk.rohdef.danske_bank;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class FoodServer {
    private static final Logger logger = LoggerFactory.getLogger(FoodServer.class);

    private final Server server;

    public FoodServer(int port) {
        logger.info("Server is starting...");

        CakePublisher cakePublisher = new CakePublisher();

        try {
            server = ServerBuilder.forPort(port)
                    .addService(new CakeFactoryService(cakePublisher))
                    .build()
                    .start();
            addShutdownHook();
            logger.info("Server started, listening on " + port);
        } catch (IOException exception) {
            logger.error("Failed to start server on port" + port, exception);
            throw new UncheckedIOException(exception);
        }

        try (ScheduledExecutorService executor = Executors.newScheduledThreadPool(1)) {
            int initialDelayInSeconds = 0;
            int delayInSeconds = 0;
            executor.scheduleWithFixedDelay(
                    () -> {
                        CakeResponse cakeResponse = CakeResponse.newBuilder()
                                .setFactory("La Glace")
                                .addAllCakes(Set.of(
                                        Cake.newBuilder().setName("Citronmåne").setPrice(35).build(),
                                        Cake.newBuilder().setName("Chokoladetrekant").setPrice(55).build(),
                                        Cake.newBuilder().setName("Hindbærsnitte").setPrice(20).build(),
                                        Cake.newBuilder().setName("Cheese cake").setPrice(60).build()
                                ))
                                .build();
                        cakePublisher.offer(
                                cakeResponse,
                                (subscriber, response) -> {
                                    subscriber.onError(new RuntimeException("response dropped due to backpressure"));
                                    return true;
                                }
                        );
                    },
                    initialDelayInSeconds, delayInSeconds, TimeUnit.SECONDS
            );
        }
    }

    private void addShutdownHook() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("Shutting down");

                    try {
                        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                    } catch (InterruptedException exception) {
                        server.shutdownNow();
                        exception.printStackTrace(System.err);
                    }

                    System.out.println("Server closed");
                }
        ));
    }

    void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }
}