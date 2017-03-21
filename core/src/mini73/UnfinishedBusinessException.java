package mini73;

/**
 * Created by shaun on 18/03/2017.
 */
public class UnfinishedBusinessException extends RuntimeException {

    private static boolean shouldThrow = true;

    public static void raise() {
        if (shouldThrow) {
            throw new UnfinishedBusinessException();
        }
    }

}
