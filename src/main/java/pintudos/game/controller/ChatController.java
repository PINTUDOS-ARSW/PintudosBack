package pintudos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pintudos.game.model.MensajeChat;
import pintudos.game.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

  @Autowired
  private ChatService chatService;

  @PostMapping("/adivinar")
  public void adivinar(@RequestBody MensajeChat palabra) {
    chatService.procesarMensaje(palabra);
  }
}
