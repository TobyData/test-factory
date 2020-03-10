package testfactory;

import com.sun.jdi.InvalidTypeException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

@Data
@Log4j2
public class ObjectFactory {
  private String className;
  private Field[] fields;
  private Method[] methods;
  private final Random random = new Random();
  private static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public ObjectFactory(Class objectClass) {
    className = objectClass.getName();
    fields = objectClass.getDeclaredFields();
    methods = objectClass.getMethods();
  }

  public Object createEmptyInstance() {
    Object obj;
    try {
      obj = Class.forName(className).newInstance();
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
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
          throw new InvalidTypeException(field.getGenericType().getTypeName() + " Is not a primitive type");
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
