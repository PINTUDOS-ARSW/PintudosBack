package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pintudos.game.model.Trace;
import pintudos.game.service.TraceService;

@Controller
public class WebSocketController {

  @Autowired
  private TraceService traceService;

  // Recibir un trazo y enviarlo a todos los clientes conectados
  @MessageMapping("/trace")
  @SendTo("/topic/traces")
  public Trace sendTrace(Trace trace) throws Exception {
    // Guardar el trazo en la base de datos
    traceService.saveTrace(trace);
    return trace; // Enviar el trazo a todos los clientes
  }
}
