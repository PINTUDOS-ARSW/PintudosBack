package pintudos.game.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pintudos.game.model.Trace;
import pintudos.game.repository.TraceRepository;

@Service
public class TraceService {

  @Autowired
  private TraceRepository traceRepository;

  public Trace saveTrace(Trace trace) {
    return traceRepository.save(trace); // Guardamos el trazo
  }

  public List<Trace> getTracesByRoom(String roomId) {
    return traceRepository.findByRoomId(roomId); // Recuperamos los trazos de la sala
  }
}
