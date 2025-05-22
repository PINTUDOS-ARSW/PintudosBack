package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pintudos.game.model.RoomRequest;

public class RoomRequestTest {

  @Test
  public void testSettersAndGetters() {
    RoomRequest request = new RoomRequest();

    request.setRoomId("room123");
    request.setPlayer("playerABC");

    assertThat(request.getRoomId()).isEqualTo("room123");
    assertThat(request.getPlayer()).isEqualTo("playerABC");
  }
}
