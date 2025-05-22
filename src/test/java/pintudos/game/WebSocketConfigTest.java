package pintudos.game;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pintudos.game.websocket.WebSocketConfig;

@SpringBootTest
public class WebSocketConfigTest {

  @Autowired
  private WebSocketConfig webSocketConfig;

  @Test
  public void contextLoads() {
    assertThat(webSocketConfig).isNotNull();
  }
}
