package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pintudos.game.model.GameRoom;
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
  public void createRoom(String roomId) {
    GameRoom room = gameRoomService.createRoom(roomId);
    messagingTemplate.convertAndSend("/topic/rooms", room); // Notificar a los clientes sobre la nueva sala
  }

  // Unirse a una sala
  @MessageMapping("/joinRoom")
  public void joinRoom(String roomId, String player) {
    GameRoom room = gameRoomService.joinRoom(roomId, player);
    if (room != null) {
      messagingTemplate.convertAndSend("/topic/rooms", room); // Enviar la información de la sala a todos los clientes
    }
  }

  // Recibir un trazo y enviarlo a todos los miembros de la sala
  @MessageMapping("/trace/{roomId}")
  @SendTo("/topic/{roomId}/traces")
  public Trace sendTrace(String roomId, Trace trace) {
    // Guardar el trazo en la base de datos
    traceService.saveTrace(trace);
    gameRoomService.addTraceToRoom(roomId, trace); // Guardar el trazo en la sala
    return trace; // Enviar el trazo a todos los jugadores en esa sala
  }
}
