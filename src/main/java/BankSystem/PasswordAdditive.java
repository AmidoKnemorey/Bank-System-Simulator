package BankSystem;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class PasswordAdditive {

    public static final int KEY_LENGTH = 512;
    private static final int ITERATIONS = 1000;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generateSalt(final int length) {
        byte[] salt = new byte[length];
        SECURE_RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static Optional<String> ahashThePlainTextPassword (String simpleTextPassword, String salt) {
        char[] simpleTextPasswordArrayOfChars = simpleTextPassword.toCharArray();
        byte[] saltArrayOfBytes = salt.getBytes();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(simpleTextPasswordArrayOfChars, saltArrayOfBytes, ITERATIONS, KEY_LENGTH);

        Arrays.fill(simpleTextPasswordArrayOfChars, Character.MIN_VALUE);

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            return Optional.of(Base64.getEncoder().encodeToString(securePassword));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            pbeKeySpec.clearPassword();
        }
    }

    public static boolean verifyThePassword(String plainTextPassword, String hashedPassword, String salt) {
        Optional<String> optEncrypted = hashThePlainTextPassword(plainTextPassword, salt);
        return optEncrypted.map(s -> s.equals(hashedPassword)).orElse(false);
    }
}