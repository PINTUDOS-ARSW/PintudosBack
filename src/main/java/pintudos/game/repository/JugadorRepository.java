package pintudos.game.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pintudos.game.model.Jugador;

@Repository
public interface JugadorRepository extends MongoRepository<Jugador, String> {
  // Buscar un jugador por su nombre
  Optional<Jugador> findByNombre(String nombre);

  // Obtener jugadores ordenados por puntuación (de mayor a menor)
  List<Jugador> findAllByOrderByPuntuacionDesc();
}
