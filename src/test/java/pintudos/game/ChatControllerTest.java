package pintudos.game;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // <-- Agrega esto
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pintudos.game.controller.ChatController;
import pintudos.game.controller.ChatController.ChatMessage;
import pintudos.game.model.GameRoom;
import pintudos.game.service.GameRoomService;

@ExtendWith(MockitoExtension.class) // <-- Agrega esto
public class ChatControllerTest {

  @Mock
  private SimpMessagingTemplate messagingTemplate;

  @Mock
  private GameRoomService gameRoomService;

  @InjectMocks
  private ChatController chatController;

  // Ya no necesitas el setUp() con MockitoAnnotations.openMocks(this)

  @Test
  public void handleChatMessage_whenRoomExists_andMessageIsCorrectWord_sendsWinnerMessage() {
    String roomId = "room1";
    ChatMessage inputMessage = new ChatMessage();
    inputMessage.setSender("Player1");
    inputMessage.setMessage("apple");

    GameRoom room = spy(new GameRoom(roomId, "Player1"));
    doReturn("apple").when(room).getWordToGuess();

    when(gameRoomService.getRoom(roomId)).thenReturn(room);

    chatController.handleChatMessage(roomId, inputMessage);

    ArgumentCaptor<ChatMessage> captor = ArgumentCaptor.forClass(
      ChatMessage.class
    );
    verify(messagingTemplate)
      .convertAndSend(eq("/topic/chat/" + roomId), captor.capture());

    ChatMessage sentMessage = captor.getValue();
    assertThat(sentMessage.getSender()).isEqualTo("System");
    assertThat(sentMessage.getMessage())
      .contains("ha adivinado la palabra y ganó");
    assertThat(sentMessage.getTimestamp()).isNotNull();
  }
}
