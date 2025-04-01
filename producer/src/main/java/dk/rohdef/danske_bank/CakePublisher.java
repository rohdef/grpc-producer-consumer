package dk.rohdef.danske_bank;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

public final class CakePublisher extends SubmissionPublisher<CakeResponse> {
    public CakePublisher() {
        super(Executors.newSingleThreadExecutor(), 5);
    }
}
