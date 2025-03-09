package pintudos.game.model;

import java.sql.Time;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "salas")
@Getter
@Setter
@Builder
public class Sala {

  @Id
  private String id;

  private String codigoSala;
  private List<String> jugadoresId;
  private Jugador creadorSala;
  private String estado;
  private List<String> palabrasSecretas;
  private int rondaActual;
  private int cantRondas;
  private Time tiempoRonda;
}
