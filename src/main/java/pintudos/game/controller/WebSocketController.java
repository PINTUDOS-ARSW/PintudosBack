package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

  @MessageMapping("/trace")
  public void sendTrace(Trace trace) {
    // Guardar el trazo en la base de datos
    traceService.saveTrace(trace);

    // Enviar el trazo a la sala específica
    String destination = "/topic/sala-" + trace.getRoomId() + "/traces";
    messagingTemplate.convertAndSend(destination, trace);
  }
}
