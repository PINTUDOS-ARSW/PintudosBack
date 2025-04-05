package pintudos.game.controller;

import java.time.LocalDateTime;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate;

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
    messagingTemplate.convertAndSend("/topic/chat/" + roomId, chatMessage);
  }
}
