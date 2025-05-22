package pintudos.game;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pintudos.game.controller.ChatController;
import pintudos.game.controller.ChatController.ChatMessage;
import pintudos.game.model.GameRoom;
import pintudos.game.service.GameRoomService;

public class ChatControllerTest {

  @Mock
  private SimpMessagingTemplate messagingTemplate;

  @Mock
  private GameRoomService gameRoomService;

  @InjectMocks
  private ChatController chatController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void handleChatMessage_whenRoomExists_andMessageIsCorrectWord_sendsWinnerMessage() {
    String roomId = "room1";
    ChatMessage inputMessage = new ChatMessage();
    inputMessage.setSender("Player1");
    inputMessage.setMessage("apple");

    // Creamos spy para controlar getWordToGuess()
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

  @Test
  public void handleChatMessage_whenRoomExists_andMessageIsNotCorrectWord_sendsNormalMessage() {
    String roomId = "room1";
    ChatMessage inputMessage = new ChatMessage();
    inputMessage.setSender("Player1");
    inputMessage.setMessage("banana");

    GameRoom room = spy(new GameRoom(roomId, "Player1"));
    doReturn("apple").when(room).getWordToGuess();

    when(gameRoomService.getRoom(roomId)).thenReturn(room);

    chatController.handleChatMessage(roomId, inputMessage);

    verify(messagingTemplate)
      .convertAndSend(eq("/topic/chat/" + roomId), any(ChatMessage.class));
  }

  @Test
  public void handleChatMessage_whenRoomDoesNotExist_doesNotSendMessage() {
    String roomId = "room1";
    ChatMessage inputMessage = new ChatMessage();
    inputMessage.setSender("Player1");
    inputMessage.setMessage("apple");

    when(gameRoomService.getRoom(roomId)).thenReturn(null);

    chatController.handleChatMessage(roomId, inputMessage);

    verify(messagingTemplate, never())
      .convertAndSend(anyString(), (Object) any());
  }

  @Test
  public void handleHintRequest_whenRoomExists_andHintIsAvailable_sendsHintMessage() {
    String roomId = "room1";
    String playerName = "Player1";

    GameRoom room = spy(new GameRoom(roomId, "Player1"));
    doReturn("A red or green fruit.").when(room).getHint();

    when(gameRoomService.getRoom(roomId)).thenReturn(room);

    chatController.handleHintRequest(roomId, playerName);

    ArgumentCaptor<ChatMessage> captor = ArgumentCaptor.forClass(
      ChatMessage.class
    );
    verify(messagingTemplate)
      .convertAndSend(eq("/topic/chat/" + roomId), captor.capture());

    ChatMessage sentMessage = captor.getValue();
    assertThat(sentMessage.getSender()).isEqualTo("System");
    assertThat(sentMessage.getMessage()).contains(playerName);
    assertThat(sentMessage.getMessage()).contains(room.getHint());
    assertThat(sentMessage.getTimestamp()).isNotNull();
  }

  @Test
  public void handleHintRequest_whenRoomDoesNotExist_doesNotSendMessage() {
    String roomId = "room1";
    String playerName = "Player1";

    when(gameRoomService.getRoom(roomId)).thenReturn(null);

    chatController.handleHintRequest(roomId, playerName);

    verify(messagingTemplate, never())
      .convertAndSend(anyString(), any(Object.class));
  }

  @Test
  public void getSecretWord_whenRoomExists_returnsWord() {
    String roomId = "room1";
    GameRoom room = spy(new GameRoom(roomId, "Player1"));
    doReturn("apple").when(room).getWordToGuess();

    when(gameRoomService.getRoom(roomId)).thenReturn(room);

    String response = chatController.getSecretWord(roomId);

    assertThat(response).isEqualTo("apple");
  }

  @Test
  public void getSecretWord_whenRoomDoesNotExist_returnsErrorMessage() {
    String roomId = "room1";

    when(gameRoomService.getRoom(roomId)).thenReturn(null);

    String response = chatController.getSecretWord(roomId);

    assertThat(response).isEqualTo("Room not found");
  }

  @Test
  public void getClue_whenClueNotGiven_returnsHintAndMarksClue() {
    String roomId = "room1";
    GameRoom room = spy(new GameRoom(roomId, "Player1"));

    doReturn(false).when(room).isClueAlreadyGiven();
    doReturn("A red or green fruit.").when(room).getHint();

    when(gameRoomService.getRoom(roomId)).thenReturn(room);

    ResponseEntity<String> response = chatController.getClue(roomId);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).isEqualTo("A red or green fruit.");
    verify(room).markClueAsGiven();
  }

  @Test
  public void getClue_whenClueAlreadyGiven_returnsMessage() {
    String roomId = "room1";
    GameRoom room = spy(new GameRoom(roomId, "Player1"));

    doReturn(true).when(room).isClueAlreadyGiven();
    doReturn("A red or green fruit.").when(room).getHint();

    when(gameRoomService.getRoom(roomId)).thenReturn(room);

    ResponseEntity<String> response = chatController.getClue(roomId);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody())
      .isEqualTo("La pista ya ha sido entregada a otro jugador.");
    verify(room, never()).markClueAsGiven();
  }
}
