package testfactory;

public class NonPrimitives {

  private NonPrimitives() {
  }

  public static boolean isNonPrimitive(Class<?> clazz) {
    return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Double.class) ||
        clazz.equals(Float.class) || clazz.equals(Boolean.class) || clazz.equals(Long.class) ||
        clazz.equals(Short.class) || clazz.equals(Character.class) || clazz.equals(Byte.class));
  }

  public static Object createRandomInstance(Class<?> clazz) {
    if (clazz.equals(String.class)) {
      return RandomUtil.createString();
    } else if (clazz.equals(Integer.class)) {
      return RandomUtil.createInteger();
    } else if (clazz.equals(Double.class)) {
      return RandomUtil.createDouble();
    } else if (clazz.equals(Float.class)) {
      return RandomUtil.createFloat();
    } else if (clazz.equals(Boolean.class)) {
      return RandomUtil.createBoolean();
    } else if (clazz.equals(Long.class)) {
      return RandomUtil.createLong();
    } else if (clazz.equals(Short.class)) {
      return RandomUtil.createShort();
    } else if (clazz.equals(Character.class)) {
      return RandomUtil.createChar();
    } else if (clazz.equals(Byte.class)) {
      return RandomUtil.createByte();
    } else {
      return null;
    }
  }
}
