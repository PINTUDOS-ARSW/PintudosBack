package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pintudos.game.model.PlayerCount;

public class PlayerCountTest {

  @Test
  public void testDefaultConstructorAndSetterGetter() {
    PlayerCount pc = new PlayerCount();

    pc.setPlayers(5);
    assertThat(pc.getPlayers()).isEqualTo(5);
  }

  @Test
  public void testConstructorWithParameter() {
    PlayerCount pc = new PlayerCount(10);
    assertThat(pc.getPlayers()).isEqualTo(10);
  }

  @Test
  public void testSetPlayers_updatesValue() {
    PlayerCount pc = new PlayerCount(0);
    pc.setPlayers(7);
    assertThat(pc.getPlayers()).isEqualTo(7);
  }
}
