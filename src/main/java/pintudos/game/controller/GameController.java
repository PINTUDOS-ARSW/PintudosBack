package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pintudos.game.model.GameRoom;
import pintudos.game.model.PlayerCount;
import pintudos.game.model.RoomRequest;
import pintudos.game.model.Trace;
import pintudos.game.service.GameRoomService;
import pintudos.game.service.TraceService;

@Controller
public class GameController {

  @Autowired
  private GameRoomService gameRoomService;

  @Autowired
  private TraceService traceService;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  // Crear una nueva sala
  @MessageMapping("/createRoom")
  public void createRoom(RoomRequest request) {
    GameRoom room = gameRoomService.createRoom(
      request.getRoomId(),
      request.getPlayer()
    );
    messagingTemplate.convertAndSend("/topic/rooms", room);
  }

  // Unirse a una sala
  @MessageMapping("/joinRoom")
  public void joinRoom(RoomRequest request) {
    GameRoom room = gameRoomService.joinRoom(
      request.getRoomId(),
      request.getPlayer()
    );

    if (room != null) {
      // Notificar cambios generales de la sala (ya lo haces)
      messagingTemplate.convertAndSend("/topic/rooms", room);

      // Enviar número de jugadores a los conectados en esa sala
      int playerCount = room.getPlayers().size();
      messagingTemplate.convertAndSend(
        "/topic/room/" + request.getRoomId() + "/players",
        new PlayerCount(playerCount)
      );
    }
  }

  // Recibir un trazo y enviarlo a todos los miembros de la sala
  @MessageMapping("/trace/{roomId}")
  public void sendTrace(@DestinationVariable String roomId, Trace trace) {
    messagingTemplate.convertAndSend("/topic/" + roomId + "/traces", trace);
  }
}
