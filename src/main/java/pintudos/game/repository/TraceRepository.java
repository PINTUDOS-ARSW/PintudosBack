package pintudos.game.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import pintudos.game.model.Trace;

public interface TraceRepository extends MongoRepository<Trace, String> {
  List<Trace> findByRoomId(String roomId); // Buscar trazos por ID de sala
}
