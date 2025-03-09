package pintudos.game.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "jugadores")
public class Jugador {

  @Id
  private String id;

  private String nombre;
  private int puntuación;
  private int salaID;
}
