package dk.rohdef.danske_bank;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;

import java.util.Objects;

public final class MessagesService extends MessagesGrpc.MessagesImplBase {
    private final MessagePublisher messagePublisher;

    public MessagesService(MessagePublisher messagePublisher) {
        Objects.requireNonNull(messagePublisher, "foo cannot be null");
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void getMessages(Empty request, StreamObserver<MessageResponse> responseObserver) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(responseObserver, "responseObserver must not be null");

        messagePublisher.subscribe(new MessageSubcriber(responseObserver));
    }
}