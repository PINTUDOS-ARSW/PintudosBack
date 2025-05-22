package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pintudos.game.model.GameRoom;
import pintudos.game.model.Trace;

public class GameRoomTest {

  private GameRoom gameRoom;

  @BeforeEach
  public void setup() {
    // Usamos "player1" como jugador inicial y "room1" como id
    gameRoom = new GameRoom("room1", "player1");
  }

  @Test
  public void testConstructor_initializesFieldsCorrectly() {
    assertThat(gameRoom.getRoomId()).isEqualTo("room1");
    assertThat(gameRoom.getPlayers()).contains("player"); // Según el código, agrega literal "player"
    assertThat(gameRoom.getTraces()).isEmpty();
    assertThat(gameRoom.getWordToGuess()).isNotNull();
    assertThat(gameRoom.getHint()).isNotNull();
    assertThat(gameRoom.isClueAlreadyGiven()).isFalse();
  }

  @Test
  public void testAddPlayer_addsNewPlayer() {
    gameRoom.addPlayer("newPlayer");
    assertThat(gameRoom.getPlayers()).contains("newPlayer");
  }

  @Test
  public void testAddPlayer_doesNotAddDuplicate() {
    int sizeBefore = gameRoom.getPlayers().size();
    gameRoom.addPlayer("player"); // Ya existe "player" literal
    assertThat(gameRoom.getPlayers()).hasSize(sizeBefore);
  }

  @Test
  public void testRemovePlayer_removesExistingPlayer() {
    gameRoom.addPlayer("toRemove");
    assertThat(gameRoom.getPlayers()).contains("toRemove");

    gameRoom.removePlayer("toRemove");
    assertThat(gameRoom.getPlayers()).doesNotContain("toRemove");
  }

  @Test
  public void testRemovePlayer_nonExistingPlayer_doesNothing() {
    int sizeBefore = gameRoom.getPlayers().size();
    gameRoom.removePlayer("nonExisting");
    assertThat(gameRoom.getPlayers()).hasSize(sizeBefore);
  }

  @Test
  public void testMarkClueAsGiven_changesFlag() {
    assertThat(gameRoom.isClueAlreadyGiven()).isFalse();
    gameRoom.markClueAsGiven();
    assertThat(gameRoom.isClueAlreadyGiven()).isTrue();
  }
}
