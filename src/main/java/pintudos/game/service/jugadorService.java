package pintudos.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import pintudos.game.model.Jugador;
import pintudos.game.repository.JugadorRepository;

public class jugadorService {

  @Autowired
  private JugadorRepository jugadorRepository;

  public Jugador registrarJugador(Jugador jugadorHost) {
    jugadorRepository.save(jugadorHost);
    return jugadorHost;
  }
}
