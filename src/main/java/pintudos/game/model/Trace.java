package pintudos.game.model;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "traces")
public class Trace {

  @Id
  private String id;

  private String roomId;
  private List<CustomPoint> points; // Usar la clase personalizada
  private String color;
  private int width;

  public Trace(
    String roomId,
    List<CustomPoint> points,
    String color,
    int width
  ) {
    this.roomId = roomId;
    this.points = points;
    this.color = color;
    this.width = width;
  }

  public Trace() {}

  // Getters y Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public List<CustomPoint> getPoints() {
    return points;
  }

  public void setPoints(List<CustomPoint> points) {
    this.points = points;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  // Métodos para convertir entre CustomPoint y Point
  public List<Point> toMongoPoints() {
    return points
      .stream()
      .map(p -> new Point(new Position(p.getLongitude(), p.getLatitude())))
      .collect(Collectors.toList());
  }

  public void fromMongoPoints(List<Point> mongoPoints) {
    this.points =
      mongoPoints
        .stream()
        .map(p ->
          new CustomPoint(
            p.getCoordinates().getValues().get(1),
            p.getCoordinates().getValues().get(0)
          )
        )
        .collect(Collectors.toList());
  }
}
