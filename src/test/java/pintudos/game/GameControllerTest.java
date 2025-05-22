package pintudos.game;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pintudos.game.controller.GameController;
import pintudos.game.model.GameRoom;
import pintudos.game.model.PlayerCount;
import pintudos.game.model.RoomRequest;
import pintudos.game.model.Trace;
import pintudos.game.service.GameRoomService;
import pintudos.game.service.TraceService;

public class GameControllerTest {

  @Mock
  private GameRoomService gameRoomService;

  @Mock
  private TraceService traceService;

  @Mock
  private SimpMessagingTemplate messagingTemplate;

  @InjectMocks
  private GameController gameController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void createRoom_callsServiceAndSendsMessage() {
    RoomRequest request = new RoomRequest();
    request.setRoomId("room1");
    request.setPlayer("player1");

    GameRoom room = mock(GameRoom.class);
    when(gameRoomService.createRoom("room1", "player1")).thenReturn(room);

    gameController.createRoom(request);

    verify(gameRoomService).createRoom("room1", "player1");
    verify(messagingTemplate).convertAndSend("/topic/rooms", room);
  }

  @Test
  public void joinRoom_existingRoom_sendsRoomAndPlayerCount() {
    RoomRequest request = new RoomRequest();
    request.setRoomId("room1");
    request.setPlayer("player1");

    GameRoom room = mock(GameRoom.class);
    when(gameRoomService.joinRoom("room1", "player1")).thenReturn(room);
    when(room.getPlayers()).thenReturn(java.util.List.of("player1", "player2"));

    gameController.joinRoom(request);

    verify(gameRoomService).joinRoom("room1", "player1");
    verify(messagingTemplate).convertAndSend("/topic/rooms", room);

    ArgumentCaptor<PlayerCount> captor = ArgumentCaptor.forClass(
      PlayerCount.class
    );
    verify(messagingTemplate)
      .convertAndSend(eq("/topic/room/room1/players"), captor.capture());

    PlayerCount sentPlayerCount = captor.getValue();
    assertThat(sentPlayerCount.getPlayers()).isEqualTo(2);
  }

  @Test
  public void joinRoom_nonExistingRoom_doesNotSendPlayerCount() {
    RoomRequest request = new RoomRequest();
    request.setRoomId("room1");
    request.setPlayer("player1");

    when(gameRoomService.joinRoom("room1", "player1")).thenReturn(null);

    gameController.joinRoom(request);

    verify(gameRoomService).joinRoom("room1", "player1");
    verify(messagingTemplate, never())
      .convertAndSend(eq("/topic/rooms"), any(Object.class));
    verify(messagingTemplate, never())
      .convertAndSend(
        (String) argThat(arg -> ((String) arg).contains("/players")),
        (Object) any()
      );
  }

  @Test
  public void sendTrace_sendsTraceToTopic() {
    String roomId = "room1";
    Trace trace = mock(Trace.class);

    gameController.sendTrace(roomId, trace);

    verify(messagingTemplate)
      .convertAndSend("/topic/" + roomId + "/traces", trace);
  }
}
