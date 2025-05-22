package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pintudos.game.model.GameRoom;
import pintudos.game.model.Trace;
import pintudos.game.service.GameRoomService;

public class GameRoomServiceTest {

  private GameRoomService gameRoomService;

  @BeforeEach
  public void setup() {
    gameRoomService = new GameRoomService();
  }

  @Test
  public void createRoom_createsAndStoresRoom() {
    GameRoom room = gameRoomService.createRoom("room1", "player1");

    assertThat(room).isNotNull();
    assertThat(room.getRoomId()).isEqualTo("room1");
    assertThat(room.getPlayers()).contains("player"); // Según constructor agrega "player"
    assertThat(gameRoomService.getRoom("room1")).isEqualTo(room);
  }

  @Test
  public void joinRoom_existingRoom_addsPlayer() {
    GameRoom room = gameRoomService.createRoom("room2", "player1");
    assertThat(room.getPlayers()).doesNotContain("newPlayer");

    GameRoom joinedRoom = gameRoomService.joinRoom("room2", "newPlayer");

    assertThat(joinedRoom).isEqualTo(room);
    assertThat(joinedRoom.getPlayers()).contains("newPlayer");
  }

  @Test
  public void joinRoom_nonExistingRoom_returnsNull() {
    GameRoom result = gameRoomService.joinRoom("nonexistent", "player");
    assertThat(result).isNull();
  }

  @Test
  public void getRoom_existingRoom_returnsRoom() {
    GameRoom room = gameRoomService.createRoom("room3", "player1");

    GameRoom found = gameRoomService.getRoom("room3");

    assertThat(found).isEqualTo(room);
  }

  @Test
  public void getRoom_nonExistingRoom_returnsNull() {
    GameRoom found = gameRoomService.getRoom("doesNotExist");
    assertThat(found).isNull();
  }

  @Test
  public void addTraceToRoom_existingRoom_addsTrace() {
    GameRoom room = gameRoomService.createRoom("room4", "player1");

    Trace trace = new Trace("room4", null, "blue", 3);
    gameRoomService.addTraceToRoom("room4", trace);

    assertThat(room.getTraces()).contains(trace);
  }

  @Test
  public void addTraceToRoom_nonExistingRoom_doesNothing() {
    Trace trace = new Trace("roomX", null, "blue", 3);

    // No exception expected
    gameRoomService.addTraceToRoom("nonexistentRoom", trace);
  }
}
