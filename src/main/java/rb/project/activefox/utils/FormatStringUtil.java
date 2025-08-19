package rb.project.activefox.utils;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class FormatStringUtil {

    @SneakyThrows
    public static String hash(String input, String algorithm) {
        MessageDigest md= MessageDigest.getInstance(algorithm);
        byte[] hashByte=md.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashByte);
    }

    public static String sha256(String input) {
        return hash(input, "SHA-256");
    }
    public static String encodeEmailBase64(String email) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(email.getBytes());
    }
    public static String genreateString(int length, boolean useLetters, boolean useNumbers){
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
