package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import pintudos.game.model.Trace;
import pintudos.game.service.TraceService;

@Controller
public class WebSocketController {

  @Autowired
  private TraceService traceService;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/ws/trace/{roomId}")
  public void sendTrace(@DestinationVariable String roomId, Trace trace) {
    messagingTemplate.convertAndSend("/topic/trace/" + roomId, trace);
  }
}
