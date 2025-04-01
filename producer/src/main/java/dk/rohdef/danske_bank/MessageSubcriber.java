package dk.rohdef.danske_bank;

import io.grpc.stub.StreamObserver;

import java.util.Objects;
import java.util.concurrent.Flow;

final class MessageSubcriber implements Flow.Subscriber<MessageResponse> {
    private Flow.Subscription subscription;
    private final StreamObserver<MessageResponse> responseObserver;

    public MessageSubcriber(StreamObserver<MessageResponse> responseObserver) {
        Objects.requireNonNull(responseObserver, "responseObserver must not be null");

        this.responseObserver = responseObserver;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        Objects.requireNonNull(subscription, "subscription must not be null");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(MessageResponse response) {
        responseObserver.onNext(response);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        responseObserver.onError(throwable);
    }

    @Override
    public void onComplete() {
        responseObserver.onCompleted();
    }
}
