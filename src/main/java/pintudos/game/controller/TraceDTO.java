package pintudos.game.controller;

import java.util.List;
import org.springframework.data.annotation.Id;
import pintudos.game.model.CustomPoint;

public class TraceDTO {

  private String roomId;
  private List<CustomPoint> points; // Usar la clase personalizada
  private String color;
  private int width;

  public TraceDTO(
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

  // Getters y Setters

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
