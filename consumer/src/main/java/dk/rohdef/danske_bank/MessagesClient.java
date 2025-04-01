package dk.rohdef.danske_bank;

import com.google.protobuf.Empty;
import io.grpc.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public final class MessagesClient {
    private static final Logger logger = LoggerFactory.getLogger(MessagesClient.class);

    private final MessagesGrpc.MessagesStub messages;

    public MessagesClient(Channel channel) {
        Objects.requireNonNull(channel, "Channel must not be null");
        messages = MessagesGrpc.newStub(channel);
    }

    public CountDownLatch messages() {
        logger.info("Trying to get messages");

        CountDownLatch countDownLatch = new CountDownLatch(1);
        messages.getMessages(
                Empty.newBuilder().build(),
                new MessageResponseObserver(countDownLatch)
        );
        return countDownLatch;
    }
}