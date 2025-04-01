package dk.rohdef.danske_bank;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class MessagesServer {
    private static final Logger logger = LoggerFactory.getLogger(MessagesServer.class);

    private final Server server;
    private final ScheduledExecutorService executor;

    public MessagesServer(int port) {
        logger.info("Server is starting...");

        MessagePublisher cakePublisher = new MessagePublisher();

        try {
            server = ServerBuilder.forPort(port)
                    .addService(new MessagesService(cakePublisher))
                    .build()
                    .start();
            addShutdownHook();
            logger.info("Server started, listening on " + port);
        } catch (IOException exception) {
            logger.error("Failed to start server on port" + port, exception);
            throw new UncheckedIOException(exception);
        }

        executor = Executors.newScheduledThreadPool(1);
        int initialDelayInSeconds = 0;
        int delayInSeconds = 1;
        executor.scheduleWithFixedDelay(
                () -> {
                    MessageResponse messageResponse = MessageResponse.newBuilder()
                            .setId(UUID.randomUUID().toString())
                            .setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                            .setGenerated(randomString())
                            .build();
                    cakePublisher.offer(
                            messageResponse,
                            (subscriber, response) -> {
                                subscriber.onError(new RuntimeException("response dropped due to backpressure"));
                                return true;
                            }
                    );
                },
                initialDelayInSeconds, delayInSeconds, TimeUnit.SECONDS
        );
        // Deliberate hack for infinite production - a "nicer" way would be a shutdown mechanism
    }

    private String randomString() {
        String alphabet = "ATCG"; // Gene nucleotides ;) Couldn't resist XD
        StringBuilder geneticRead = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            int index = rand.nextInt(alphabet.length());
            geneticRead.append(alphabet.charAt(index));
        }
        return geneticRead.toString();
    }

    private void addShutdownHook() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> {
                    System.out.println("Shutting down");

                    try {
                        server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
                    } catch (InterruptedException exception) {
                        server.shutdownNow();
                        exception.printStackTrace(System.err);
                    }

                    executor.shutdown();

                    System.out.println("Server closed");
                }
        ));
    }

    void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }
}