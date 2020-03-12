package testfactory;

import java.util.Random;

public class RandomUtil {
  private static Random random =  new Random();
  private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static String createString() {
      int length = random.nextInt(32);
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < length; i++) {
        stringBuilder.append(RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING.length())));
      }
      return stringBuilder.toString();
  }

  public static char createChar() {
    return RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING.length()));
  }

  public static Integer createInteger() {
    return random.nextInt(Integer.MAX_VALUE);
  }

  public static Byte createByte() {
    return (byte) random.nextInt(Byte.MAX_VALUE);
  }

  public static Short createShort() {
    return (short) random.nextInt(Short.MAX_VALUE);
  }

  public static Long createLong() {
    return random.nextLong();
  }

  public static Boolean createBoolean() {
    return random.nextBoolean();
  }

  public static Float createFloat() {
    return random.nextFloat();
  }

  public static Double createDouble() {
    return random.nextDouble();
  }
}
