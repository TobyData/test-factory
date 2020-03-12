package testfactory;

import org.junit.jupiter.api.Test;
import resources.TestClass;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PrimitivesTest {

  @Test
  public void testListIsNotPrimitive() {
    ObjectFactory factory = new ObjectFactory(TestClass.class);
    Object obj = factory.createRandomInstance();
    Field listField = factory.getFields()[2];

    assertFalse(Primitives.isPrimitive(listField.getDeclaringClass()));
  }

  @Test
  public void testCheckIfPrimitiveUsingClassEqualsUsingField() {
    ObjectFactory factory = new ObjectFactory(TestClass.class);
    Object obj = factory.createRandomInstance();
    Field listField = factory.getFields()[2];

    assertEquals(Primitives.isPrimitive(listField), Primitives.isPrimitive(listField.getType()));
  }
}