package pintudos.game.controller;

import java.util.List;
import lombok.Data;
import pintudos.game.model.CustomPoint;

@Data
public class TraceDTO {

  private String roomId;
  private List<CustomPoint> points; // Usar la clase personalizada
  private String color;
  private int width;
}
