package automation.testing.framework.api.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Supplier;

public class OWSecureHashUtils {

  private static final String ALGORITHM = "SHA-512";

  public static void main(String[] args) {
    // plug in string you'd like one way hash string to hash
    final String input = "12345678";
    System.out.println(String.format("Hash Value of [%s] => %s", input, oneWayHash(input)));
  }

  public static String oneWayHash(final String clearString) {
    Supplier<MessageDigest> digestSupplier =
        () -> {
          try {
            return MessageDigest.getInstance(ALGORITHM, new BouncyCastleProvider());
          } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                String.format("Algorithm %s not supported %s", ALGORITHM, e.getMessage()));
          }
        };
    Supplier<String> hashStringSupplier =
        () -> {
          MessageDigest messageDigest = digestSupplier.get();
          return new String(
              Hex.encode(messageDigest.digest(clearString.getBytes(StandardCharsets.UTF_8))));
        };
    return hashStringSupplier.get();
  }
}
