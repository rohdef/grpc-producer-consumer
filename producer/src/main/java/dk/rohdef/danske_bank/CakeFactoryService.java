package dk.rohdef.danske_bank;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Flow;

public final class CakeFactoryService extends CakeFactoryGrpc.CakeFactoryImplBase {
    private static final Logger logger = LoggerFactory.getLogger(CakeFactoryService.class);
    private final CakePublisher cakePublisher;

    public CakeFactoryService(CakePublisher cakePublisher) {
        Objects.requireNonNull(cakePublisher, "foo cannot be null");
        this.cakePublisher = cakePublisher;
    }

    @Override
    public void getCakeOffers(CakeRequest request, StreamObserver<CakeResponse> responseObserver) {
        Objects.requireNonNull(request, "request must not be null");
        Objects.requireNonNull(responseObserver, "responseObserver must not be null");

        logger.info("Saying hello to {}", request.getRequester());

        cakePublisher.subscribe(new GreetingSubcriber(responseObserver));
    }

    private final static class GreetingSubcriber implements Flow.Subscriber<CakeResponse> {
        private Flow.Subscription subscription;
        private final StreamObserver<CakeResponse> responseObserver;

        public GreetingSubcriber(StreamObserver<CakeResponse> responseObserver) {
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
        public void onNext(CakeResponse response) {
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
}