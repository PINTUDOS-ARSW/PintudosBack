package pintudos.game.service;

import java.sql.Time;
import java.util.Optional;
import org.springframework.stereotype.Service;
import pintudos.game.model.Jugador;
import pintudos.game.model.Sala;
import pintudos.game.repository.JugadorRepository;
import pintudos.game.repository.SalaRepository;

@Service
public class SalaService {

  private SalaRepository salaRepository;
  private JugadorRepository jugadorRepository;

  public boolean unirseASala(String codigoSala, Jugador jugador) {
    // Verificamos si el jugador existe
    if (jugadorRepository.existsById(jugador.getId())) {
      // Buscamos la sala por su código
      Optional<Sala> salaOpt = salaRepository.findByCodigo(codigoSala);

      if (salaOpt.isPresent()) {
        Sala sala = salaOpt.get();

        // Verificamos que la sala no esté llena y esté en estado "esperando"
        if (
          sala.getEstado().equals("esperando") &&
          !sala.getJugadoresId().contains(jugador.getId())
        ) {
          // Agregamos al jugador
          sala.getJugadoresId().add(jugador.getId());

          // Guardamos la sala actualizada
          salaRepository.save(sala);
          return true; // El jugador se unió exitosamente
        }
      }
      return false; // Sala no encontrada o ya no está disponible
    }
    return false; // El jugador no existe
  }

  public Sala crearSala(
    Jugador jugador,
    Time tiempoPorRonda,
    int numeroDeRondas,
    String string
  ) {
    Sala sala = Sala
      .builder()
      .codigoSala("codigo")
      .creadorSala(jugador)
      .estado(string)
      .rondaActual(0)
      .cantRondas(numeroDeRondas)
      .tiempoRonda(tiempoPorRonda)
      .build();
    salaRepository.save(sala);
    return sala;
  }
}
