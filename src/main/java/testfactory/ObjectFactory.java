package testfactory;

import lombok.Data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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

  public Object createRandomInstance() {
    Object obj;
    if (NonPrimitives.isNonPrimitive(targetClass)) {
      obj = NonPrimitives.createRandomInstance(targetClass);
    } else {
      obj = createEmptyInstance();
      setAllFields(obj);
    }
    return obj;
  }

  private void setAllFields(Object obj) {
    Arrays.stream(fields).forEach(field -> {
      setField(field, obj);
    });
  }

  private Constructor<?> getNoArgsConstructor(Class<?> clazz) {
    for (Constructor<?> constructor : clazz.getConstructors()) {
      if (constructor.getParameterCount() == 0) {
        return constructor;
      }
    }
    return null;
  }

  private void setField(Field field, Object obj) {
    field.setAccessible(true);
    try {
      if (Primitives.isPrimitive(field)) {
        Primitives.setPrimitiveField(field, obj);
      }
      else if (NonPrimitives.isNonPrimitive(field.getType())) {
        field.set(obj, NonPrimitives.createRandomInstance(field.getType()));
      }
      else if (getNoArgsConstructor(field.getType()) != null) {
        field.set(obj, Objects.requireNonNull(getNoArgsConstructor(field.getType())).newInstance());
      } else if (field.getType().equals(List.class)) {
        Class<?> elementType;
        if (field.getGenericType() instanceof ParameterizedType) {
          ParameterizedType pt = (ParameterizedType) field.getGenericType();
          Type[] fieldArgTypes = pt.getActualTypeArguments();
          if (fieldArgTypes.length == 1) {
            elementType = (Class<?>) fieldArgTypes[0];
            ObjectFactory listFactory = new ObjectFactory(elementType);
          }
          field.set(obj, new ArrayList<>());
        }
      } else {
        field.set(obj, null);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
