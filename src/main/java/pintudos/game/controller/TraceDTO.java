package pintudos.game.controller;

import com.mongodb.client.model.geojson.Point;
import java.util.List;
import pintudos.game.model.CustomPoint;

public class TraceDTO {

  private String roomId;
  private List<CustomPoint> points;
  private String color;
  private int width;

  public TraceDTO() {}

  // Getters y setters
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
}
