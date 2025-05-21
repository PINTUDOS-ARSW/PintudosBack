package pintudos.game.controller;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pintudos.game.model.CustomPoint;

@Data
@Getter
@Setter
public class TraceDTO {

  private String roomId;
  private List<CustomPoint> points; // Usar la clase personalizada
  private String color;
  private int width;
}
