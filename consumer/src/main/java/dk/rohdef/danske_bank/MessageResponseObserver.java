package dk.rohdef.danske_bank;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

final class MessageResponseObserver implements StreamObserver<MessageResponse> {
    private static final Logger logger = LoggerFactory.getLogger(MessageResponseObserver.class);

    private final CountDownLatch countDownLatch;

    public MessageResponseObserver(CountDownLatch countDownLatch) {
        Objects.requireNonNull(countDownLatch, "CountDownLatch must not be null");
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(MessageResponse message) {
        String id = message.getId();
        String timestamp = message.getTimestamp();
        String generated = message.getGenerated();

        logger.info("{} - [{}] {}", timestamp, id, generated);
    }

    @Override
    public void onError(Throwable t) {
        logger.error("Failed reading message response", t);
    }

    @Override
    public void onCompleted() {
        logger.info("Message request completed");
        countDownLatch.countDown();
    }
}
