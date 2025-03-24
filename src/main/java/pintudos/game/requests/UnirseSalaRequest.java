package pintudos.game.requests;

import lombok.Getter;
import lombok.Setter;
import pintudos.game.model.Jugador;

@Getter
@Setter
public class UnirseSalaRequest {

  private String codigoSala;
  private Jugador jugador;
}
