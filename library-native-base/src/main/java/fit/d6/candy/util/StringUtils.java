package fit.d6.candy.util;

import fit.d6.candy.exception.UtilException;

import java.util.Random;
import java.util.UUID;

public final class StringUtils {

    private final static Random RANDOM = new Random();
    private final static String RANDOM_DICTIONARY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++)
            builder.append(RANDOM_DICTIONARY.charAt(RANDOM.nextInt(RANDOM_DICTIONARY.length())));
        return builder.toString();
    }

    public static UUID from32String(String uuid) {
        if (uuid.length() != 32)
            throw new UtilException("It is not a legal uuid");
        return UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
    }

    private StringUtils() {
    }

}
