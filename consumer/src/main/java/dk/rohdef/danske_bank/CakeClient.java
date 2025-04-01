package dk.rohdef.danske_bank;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public final class CakeClient {
    private static final Logger logger = LoggerFactory.getLogger(CakeClient.class);

    private final CakeFactoryGrpc.CakeFactoryStub cakeFactory;

    public CakeClient(Channel channel) {
        Objects.requireNonNull(channel, "Channel must not be null");
        this.cakeFactory = CakeFactoryGrpc.newStub(channel);
    }

    public CountDownLatch greet(String name) {
        Objects.requireNonNull(name, "name must not be null");
        logger.info("Trying to get cake offers for {}", name);

        CakeRequest request = CakeRequest.newBuilder().setRequester(name).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        cakeFactory.getCakeOffers(request, new HelloResponseObserver(countDownLatch));
        return countDownLatch;
    }

    private static final class HelloResponseObserver implements StreamObserver<CakeResponse> {
        private static final Logger logger = LoggerFactory.getLogger(HelloResponseObserver.class);

        private final CountDownLatch countDownLatch;

        public HelloResponseObserver(CountDownLatch countDownLatch) {
            Objects.requireNonNull(countDownLatch, "CountDownLatch must not be null");
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void onNext(CakeResponse response) {
            String cakes = response.getCakesList().stream()
                    .map(cake -> String.format("%s: %d", cake.getName(), cake.getPrice()))
                    .collect(Collectors.joining("\n\t"));

            logger.info("Cake offers recieved\n{} offers:\n\t{}", response.getFactory(), cakes);
        }

        @Override
        public void onError(Throwable t) {
            logger.error("Failed reading cake response", t);
        }

        @Override
        public void onCompleted() {
            logger.info("Cake requests completed");
            countDownLatch.countDown();
        }
    }
}