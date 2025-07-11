package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.function.Supplier;

public class RetryUtil {
    private static final Logger logger = LoggerFactory.getLogger(RetryUtil.class);

    public static <T> T retry(Supplier<T> action, int maxAttempts, Duration timeout) {
        Exception lastException = null;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                logger.debug("Attempt {} of {}", attempt, maxAttempts);
                return action.get();
            } catch (Exception e) {
                lastException = e;
                logger.warn("Attempt {} failed: {}", attempt, e.getMessage());
                if (attempt < maxAttempts) {
                    try {
                        Thread.sleep(timeout.toMillis());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(ie);
                    }
                }
            }
        }
        logger.error("All {} attempts failed", maxAttempts);
        throw new RuntimeException("Retry failed after " + maxAttempts + " attempts", lastException);
    }

    public static void retryVoid(Runnable action, int maxAttempts, Duration timeout) {
        retry(() -> {
            action.run();
            return null;
        }, maxAttempts, timeout);
    }
}
