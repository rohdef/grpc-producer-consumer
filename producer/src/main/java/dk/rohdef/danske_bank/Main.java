package dk.rohdef.danske_bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        logger.info("Hello, I am Service A; I am the producer!");

        FoodServer server = new FoodServer(50051);
        server.blockUntilShutdown();
    }
}