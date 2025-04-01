package pintudos.game.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import pintudos.game.model.GameRoom;
import pintudos.game.model.Trace;

@Service
public class GameRoomService {

  private Map<String, GameRoom> rooms = new HashMap<>();

  // Crear una nueva sala
  public GameRoom createRoom(String roomId) {
    GameRoom room = new GameRoom(roomId);
    rooms.put(roomId, room);
    return room;
  }

  // Unirse a una sala
  public GameRoom joinRoom(String roomId, String player) {
    GameRoom room = rooms.get(roomId);
    if (room != null) {
      room.addPlayer(player);
    }
    return room;
  }

  // Obtener una sala por ID
  public GameRoom getRoom(String roomId) {
    return rooms.get(roomId);
  }

  // Guardar un trazo en la sala
  public void addTraceToRoom(String roomId, Trace trace) {
    GameRoom room = rooms.get(roomId);
    if (room != null) {
      room.addTrace(trace);
    }
  }
}
