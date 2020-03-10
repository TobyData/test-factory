package testfactory;

import org.junit.jupiter.api.Test;
import resources.OnlyPrimitives;
import resources.TestClass;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectFactoryTest {

  @Test
  public void testObjectName() {
    ObjectFactory objectFactory = new ObjectFactory(String.class);
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
  public void setPrimitiveFields() {
    ObjectFactory factory = new ObjectFactory(OnlyPrimitives.class);
    Object obj = factory.createEmptyInstance();
    OnlyPrimitives primitives = (OnlyPrimitives) obj;
    factory.setAllPrimitiveFields(primitives);
    assertDoesNotThrow(() -> factory.setAllPrimitiveFields(primitives));
    assertNotNull(primitives.getStringField());
  }
}