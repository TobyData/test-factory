package testfactory;

import java.lang.reflect.Field;
import java.util.Random;

class Primitives {
  private static final Random random = new Random();
  private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  private Primitives() {
  }

  public static boolean isPrimitive(Field field) {
    switch (field.getGenericType().getTypeName()) {
      case "int":
      case "float":
      case "long":
      case "short":
      case "char":
      case "byte":
      case "boolean":
        return true;
      default:
        return false;
    }
  }

  public static void setPrimitiveField(Field field, Object obj) {
    field.setAccessible(true);
    try {
      switch (field.getGenericType().getTypeName()) {
        case "int":
          field.setInt(obj, RandomUtil.createInteger());
          break;
        case "boolean":
          field.setBoolean(obj, RandomUtil.createBoolean());
          break;
        case "byte":
          field.setByte(obj, RandomUtil.createByte());
          break;
        case "char":
          field.setChar(obj, RandomUtil.createChar());
          break;
        case "short":
          field.setShort(obj, RandomUtil.createShort());
          break;
        case "long":
          field.setLong(obj, RandomUtil.createLong());
          break;
        case "float":
          field.setFloat(obj, RandomUtil.createFloat());
          break;
      }
    } catch (Exception e) {
        e.printStackTrace();
    }
  }

}
