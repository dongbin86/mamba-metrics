package mamba.metricsservice.timeline;


/**
 * Created by dongbin on 2016/10/10.
 */
public class NameValuePair {
  String name;
  Object value;

  public NameValuePair(String name, Object value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Get the name.
   * @return The name.
   */
  public String getName() {

    return name;
  }

  /**
   * Get the value.
   * @return The value.
   */
  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "{ name: " + name + ", value: " + value + " }";
  }
}
