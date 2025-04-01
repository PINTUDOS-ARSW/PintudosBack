package pintudos.game.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pintudos.game.model.Trace;
import pintudos.game.service.TraceService;

@RestController
@RequestMapping("/traces")
@CrossOrigin("*") // Permite peticiones desde cualquier frontend
public class TraceController {

  @Autowired
  private TraceService traceService;

  // Guardar un trazo
  @PostMapping
  public Trace saveTrace(@RequestBody Trace trace) {
    return traceService.saveTrace(trace); // Guardamos el trazo
  }

  // Obtener los trazos de una sala
  @GetMapping("/{roomId}")
  public List<Trace> getTraces(@PathVariable String roomId) {
    return traceService.getTracesByRoom(roomId); // Recuperamos los trazos de la sala
  }
}
