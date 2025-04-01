package dk.rohdef.danske_bank;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

public final class MessagePublisher extends SubmissionPublisher<MessageResponse> {
    public MessagePublisher() {
        super(Executors.newSingleThreadExecutor(), 5);
    }
}
