package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pintudos.game.model.CustomPoint;
import pintudos.game.model.Trace;

public class TraceTest {

  private Trace trace;
  private List<CustomPoint> customPoints;

  @BeforeEach
  public void setup() {
    customPoints =
      Arrays.asList(new CustomPoint(10.0, 20.0), new CustomPoint(30.0, 40.0));

    trace = new Trace("room1", customPoints, "red", 5);
  }

  @Test
  public void testConstructorAndGetters() {
    assertThat(trace.getRoomId()).isEqualTo("room1");
    assertThat(trace.getPoints()).isEqualTo(customPoints);
    assertThat(trace.getColor()).isEqualTo("red");
    assertThat(trace.getWidth()).isEqualTo(5);
  }

  @Test
  public void testSetters() {
    trace.setRoomId("room2");
    assertThat(trace.getRoomId()).isEqualTo("room2");

    List<CustomPoint> newPoints = Arrays.asList(new CustomPoint(50.0, 60.0));
    trace.setPoints(newPoints);
    assertThat(trace.getPoints()).isEqualTo(newPoints);

    trace.setColor("blue");
    assertThat(trace.getColor()).isEqualTo("blue");

    trace.setWidth(10);
    assertThat(trace.getWidth()).isEqualTo(10);

    trace.setId("12345");
    assertThat(trace.getId()).isEqualTo("12345");
  }

  @Test
  public void testToMongoPoints_convertsCorrectly() {
    List<Point> mongoPoints = trace.toMongoPoints();

    assertThat(mongoPoints).hasSize(customPoints.size());

    for (int i = 0; i < mongoPoints.size(); i++) {
      Point p = mongoPoints.get(i);
      CustomPoint cp = customPoints.get(i);

      // Verificamos que longitude = position[0], latitude = position[1]
      List<Double> coords = p.getCoordinates().getValues();
      assertThat(coords.get(0)).isEqualTo(cp.getLongitude());
      assertThat(coords.get(1)).isEqualTo(cp.getLatitude());
    }
  }

  @Test
  public void testFromMongoPoints_convertsCorrectly() {
    // Creamos puntos mongo con posiciones (longitude, latitude)
    List<Point> mongoPoints = Arrays.asList(
      new Point(new Position(70.0, 80.0)),
      new Point(new Position(90.0, 100.0))
    );

    trace.fromMongoPoints(mongoPoints);

    List<CustomPoint> cps = trace.getPoints();

    assertThat(cps).hasSize(mongoPoints.size());

    for (int i = 0; i < cps.size(); i++) {
      CustomPoint cp = cps.get(i);
      Point p = mongoPoints.get(i);

      assertThat(cp.getLongitude())
        .isEqualTo(p.getCoordinates().getValues().get(0));
      assertThat(cp.getLatitude())
        .isEqualTo(p.getCoordinates().getValues().get(1));
    }
  }
}
