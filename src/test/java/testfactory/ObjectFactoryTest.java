package testfactory;

import org.junit.jupiter.api.Test;
import resources.NestedClasses;
import resources.OnlyPrimitivesAndString;
import resources.TestClass;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectFactoryTest {

  @Test
  public void testObjectClassIsDeterminedCorrectly() {
    ObjectFactory stringFactory = new ObjectFactory(String.class);
    ObjectFactory testClassFactory = new ObjectFactory(TestClass.class);

    assertEquals(String.class, stringFactory.getTargetClass());
    assertEquals(TestClass.class, testClassFactory.getTargetClass());
  }

  @Test
  public void testGetClassFields() {
    TestClass t = new TestClass();
    ObjectFactory factory = new ObjectFactory(TestClass.class);

    assertEquals(3, factory.getFields().length);
  }

  @Test
  public void testInstantiateEmptyTestClass() {
    ObjectFactory factory = new ObjectFactory(TestClass.class);
    Object obj = factory.createEmptyInstance();
    TestClass t = (TestClass) obj;

    assertNotNull(t);
  }

  @Test
  public void testSetPrimitiveFieldsAndStringField() {
    ObjectFactory factory = new ObjectFactory(OnlyPrimitivesAndString.class);
    Object obj = factory.createRandomInstance();
    OnlyPrimitivesAndString primitives = (OnlyPrimitivesAndString) obj;

    assertNotNull(primitives.getStringField());
  }

  @Test
  public void testCreateRandomInstanceOfTestClass() {
    ObjectFactory factory = new ObjectFactory(TestClass.class);
    Object obj = factory.createRandomInstance();
    TestClass t = (TestClass) obj;

    assertNotNull(t);
    assertNotNull(t.getName());
  }

  // TODO: implement test and functionality
  @Test
  public void testInstantiateListField() throws NoSuchFieldException {
    ObjectFactory factory = new ObjectFactory(TestClass.class);
    Object obj = factory.createRandomInstance();
    TestClass t = (TestClass) obj;
    Field field = t.getClass().getDeclaredField("friends");

    // System.out.println(field.getGenericType().getTypeName());
    // System.out.println(t.toString());
  }

  @Test
  public void testStringFactory() {
    ObjectFactory factory = new ObjectFactory(String.class);
    Object obj = factory.createRandomInstance();
    String s = (String) obj;

    assertNotNull(s);
  }
}