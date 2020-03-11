package testfactory;

import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

@Data
public class ObjectFactory {

  private Class<?> targetClass;
  private Field[] fields;
  private final Random random = new Random();
  private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public ObjectFactory(Class objectClass) {
    targetClass = objectClass;
    fields = objectClass.getDeclaredFields();
  }

  public Object createEmptyInstance() {
    Constructor<?> constructor = getNoArgsConstructor(targetClass);
    if (constructor != null) {
      try {
        return constructor.newInstance();
      } catch (Exception e) {
        return null;
      }
    }
    return null;
  }

  public void setAllPrimitiveFields(Object obj) {
    Arrays.stream(fields).forEach(field -> {
      setPrimitiveField(field, obj);
    });
  }

  private String randomString() {
    int length = random.nextInt(32);
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING.length() - 1)));
    }
    return stringBuilder.toString();
  }

  private Constructor<?> getNoArgsConstructor(Class<?> clazz) {
    for (Constructor<?> constructor : clazz.getConstructors()) {
      if (constructor.getParameterCount() == 0) {
        return constructor;
      }
    }
    return null;
  }

  private void setPrimitiveField(Field field, Object obj) {
    field.setAccessible(true);
    try {
      switch (field.getGenericType().getTypeName()) {
        case "int":
          field.setInt(obj, random.nextInt());
          break;
        case "boolean":
          field.setBoolean(obj, random.nextBoolean());
          break;
        case "byte":
          field.setByte(obj, (byte) random.nextInt(255));
          break;
        case "char":
          field.setChar(obj, RANDOM_STRING.charAt(random.nextInt(RANDOM_STRING.length() - 1)));
          break;
        case "short":
          field.setShort(obj, (short) random.nextInt());
          break;
        case "long":
          field.setLong(obj, random.nextLong());
          break;
        case "float":
          field.setFloat(obj, random.nextFloat());
          break;
        case "java.lang.String":
          field.set(obj, randomString());
          break;
        default:
          Constructor<?> noArgsConstructor = getNoArgsConstructor(field.getType());
          if (noArgsConstructor != null) {
            field.set(obj, noArgsConstructor.newInstance());
          } else {
            field.set(obj, null);
          }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
