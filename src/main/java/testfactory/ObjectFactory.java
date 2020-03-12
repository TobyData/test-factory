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
    if (NonPrimitives.isAJavaNonPrimitive(targetClass)) {
      return NonPrimitives.createRandomInstance(targetClass);
    }
    Object obj = createEmptyInstance();
    setAllFields(obj);
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
      if (field.getType().equals(List.class)) {
        Class<?> elementType;
        if (field.getGenericType() instanceof ParameterizedType) {
          ParameterizedType pt = (ParameterizedType) field.getGenericType();
          Type[] fieldArgTypes = pt.getActualTypeArguments();
          ArrayList list = new ArrayList();
          if (fieldArgTypes.length == 1) {
            elementType = (Class<?>) fieldArgTypes[0];
            ObjectFactory listFactory = new ObjectFactory(elementType);
            list.add(listFactory.createRandomInstance());
            list.add(listFactory.createRandomInstance());
          }
          field.set(obj, list);
        }
      }
      else if (Primitives.isPrimitive(field)) {
        Primitives.setPrimitiveField(field, obj);
      }
      else {
        field.set(obj, NonPrimitives.createRandomInstance(field.getType()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
