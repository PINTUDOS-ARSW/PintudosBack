package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pintudos.game.model.Jugador;
import pintudos.game.model.Sala;
import pintudos.game.requests.CrearSalaRequest;
import pintudos.game.requests.UnirseSalaRequest;
import pintudos.game.service.SalaService;
import pintudos.game.service.jugadorService;

@RequestMapping("/sala")
public class SalaController {

  @Autowired
  private SalaService salaService;

  @Autowired
  private jugadorService jugadorService;

  // Unirse a una sala
  @PostMapping("/unirse")
  public ResponseEntity<String> unirseASala(
    @RequestBody UnirseSalaRequest request
  ) {
    boolean exito = salaService.unirseASala(
      request.getCodigoSala(),
      request.getJugador()
    );
    return exito
      ? ResponseEntity.ok("Unido con éxito")
      : ResponseEntity.badRequest().body("Código de sala inválido");
  }

  // Crear una nueva sala
  @PostMapping("/crear")
  public ResponseEntity<Sala> crearSala(@RequestBody CrearSalaRequest request) {
    Jugador jugador = jugadorService.registrarJugador(request.getJugadorHost());
    Sala sala = salaService.crearSala(
      jugador,
      request.getTiempoPorRonda(),
      request.getNumeroDeRondas(),
      "Creada"
    );
    return ResponseEntity.ok(sala);
  }
}
