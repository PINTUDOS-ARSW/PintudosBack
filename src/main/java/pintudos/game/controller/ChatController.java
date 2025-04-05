package pintudos.game.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import pintudos.game.model.GameRoom;
import pintudos.game.service.GameRoomService;

@Controller
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  private GameRoomService gameRoomService;

  public ChatController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  public static class ChatMessage {

    private String sender;
    private String message;
    private LocalDateTime timestamp;

    // Getters y setters

    public String getSender() {
      return sender;
    }

    public void setSender(String sender) {
      this.sender = sender;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public LocalDateTime getTimestamp() {
      return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
      this.timestamp = timestamp;
    }
  }

  @MessageMapping("/chat/{roomId}")
  public void handleChatMessage(
    @DestinationVariable String roomId,
    @Payload ChatMessage chatMessage
  ) {
    chatMessage.setTimestamp(LocalDateTime.now());

    // Obtener la sala actual
    GameRoom room = gameRoomService.getRoom(roomId);
    if (room != null) {
      // Verificar si el mensaje coincide con la palabra a adivinar
      if (
        room.getWordToGuess() != null &&
        room.getWordToGuess().equalsIgnoreCase(chatMessage.getMessage())
      ) {
        // Enviar mensaje indicando que alguien ganó
        ChatMessage winnerMessage = new ChatMessage();
        winnerMessage.setSender("System");
        winnerMessage.setMessage(
          chatMessage.getSender() + " ha adivinado la palabra y ganó!"
        );
        winnerMessage.setTimestamp(LocalDateTime.now());

        messagingTemplate.convertAndSend(
          "/topic/chat/" + roomId,
          winnerMessage
        );
      } else {
        // Enviar el mensaje normal al chat
        messagingTemplate.convertAndSend("/topic/chat/" + roomId, chatMessage);
      }
    }
  }

  @MessageMapping("/hint/{roomId}")
  public void handleHintRequest(
    @DestinationVariable String roomId,
    @Payload String playerName
  ) {
    // Obtener la sala actual
    GameRoom room = gameRoomService.getRoom(roomId);
    if (room != null && room.getHint() != null) {
      // Crear un mensaje para enviar la pista
      ChatMessage hintMessage = new ChatMessage();
      hintMessage.setSender("System");
      hintMessage.setMessage(
        playerName + " solicitó la pista: " + room.getHint()
      );
      hintMessage.setTimestamp(LocalDateTime.now());

      // Enviar el mensaje al chat de la sala
      messagingTemplate.convertAndSend("/topic/chat/" + roomId, hintMessage);
    }
  }
  @GetMapping("/game/{roomId}/secret-word")
  @ResponseBody
  public String getSecretWord(@PathVariable String roomId) {
    GameRoom room = gameRoomService.getRoom(roomId);
    if (room != null) {
      return room.getWordToGuess();
    }
    return "Room not found";
  }
  
}
