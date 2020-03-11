package resources;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class TestClass {

  private String name;
  private int age;
  private List<String> friends;
}
