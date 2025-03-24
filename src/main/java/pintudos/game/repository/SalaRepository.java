package pintudos.game.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pintudos.game.model.Jugador;
import pintudos.game.model.Sala;

@Repository
public interface SalaRepository extends MongoRepository<Sala, String> {
  // Buscar una sala por su código
  Optional<Sala> findByCodigo(String codigo);

  // Buscar salas en estado "en_juego"
  List<Sala> findByEstado(String estado);

  void agregarJugador(String codigoSala, Jugador jugador);
}
