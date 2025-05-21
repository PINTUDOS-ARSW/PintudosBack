package pintudos.game.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CustomPoint {

  private double latitude;
  private double longitude;
  private String type; // Campo opcional para JSON

  public CustomPoint() {}

  @JsonCreator
  public CustomPoint(
    @JsonProperty("type") String type,
    @JsonProperty("coordinates") List<Double> coordinates
  ) {
    this.type = type; // Ignorar o validar que sea "Point"
    this.longitude = coordinates.get(0);
    this.latitude = coordinates.get(1);
  }

  public CustomPoint(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
