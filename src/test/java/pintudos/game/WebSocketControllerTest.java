package pintudos.game;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pintudos.game.controller.WebSocketController;
import pintudos.game.model.Trace;
import pintudos.game.service.TraceService;

public class WebSocketControllerTest {

  @Mock
  private TraceService traceService;

  @Mock
  private SimpMessagingTemplate messagingTemplate;

  @InjectMocks
  private WebSocketController webSocketController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void sendTrace_sendsMessageToCorrectTopic() {
    String roomId = "room1";
    Trace trace = mock(Trace.class);

    webSocketController.sendTrace(roomId, trace);

    verify(messagingTemplate).convertAndSend("/topic/trace/" + roomId, trace);
  }
}
