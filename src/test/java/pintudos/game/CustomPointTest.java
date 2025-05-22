package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import pintudos.game.model.CustomPoint;

public class CustomPointTest {

  @Test
  public void testDefaultConstructorAndSettersGetters() {
    CustomPoint point = new CustomPoint();

    point.setLatitude(10.5);
    point.setLongitude(-75.3);
    point.setType("Point");

    assertThat(point.getLatitude()).isEqualTo(10.5);
    assertThat(point.getLongitude()).isEqualTo(-75.3);
    assertThat(point.getType()).isEqualTo("Point");
  }

  @Test
  public void testConstructorWithLatitudeLongitude() {
    CustomPoint point = new CustomPoint(1.23, 4.56);

    assertThat(point.getLatitude()).isEqualTo(1.23);
    assertThat(point.getLongitude()).isEqualTo(4.56);
    assertThat(point.getType()).isNull(); // No se asigna tipo en este constructor
  }

  @Test
  public void testJsonCreatorConstructor_validCoordinates() {
    String type = "Point";
    List<Double> coordinates = Arrays.asList(-70.0, 40.0);

    CustomPoint point = new CustomPoint(type, coordinates);

    assertThat(point.getType()).isEqualTo(type);
    assertThat(point.getLongitude()).isEqualTo(-70.0);
    assertThat(point.getLatitude()).isEqualTo(40.0);
  }

  @Test
  public void testJsonCreatorConstructor_withInvalidType_storesType() {
    String type = "InvalidType";
    List<Double> coordinates = Arrays.asList(1.0, 2.0);

    CustomPoint point = new CustomPoint(type, coordinates);

    // El constructor solo asigna, no valida el tipo
    assertThat(point.getType()).isEqualTo(type);
    assertThat(point.getLongitude()).isEqualTo(1.0);
    assertThat(point.getLatitude()).isEqualTo(2.0);
  }
}
