package pintudos.game.requests;

import java.sql.Time;
import lombok.Getter;
import lombok.Setter;
import pintudos.game.model.Jugador;

@Getter
@Setter
public class CrearSalaRequest {

  private String codigoSala;
  private Jugador jugadorHost;
  private Time tiempoPorRonda;
  private int numeroDeRondas;
}
