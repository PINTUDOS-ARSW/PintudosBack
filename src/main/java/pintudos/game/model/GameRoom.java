package pintudos.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRoom {

  private String roomId;
  private List<String> players; // Listado de jugadores conectados
  private List<Trace> traces; // Lista de trazos realizados
  private String wordToGuess;
  private String hint; // Pista de la palabra
  private boolean clueAlreadyGiven; // Indica si la pista ya fue dada

  public GameRoom(String roomId, String player) {
    this.roomId = roomId;
    this.players = new ArrayList<>();
    this.traces = new ArrayList<>();
    this.players.add("player");
    clueAlreadyGiven = false; // Inicialmente, la pista no ha sido dada

    // Mapa de palabras y pistas
    Map<String, String> wordsWithHints = new HashMap<>();
    wordsWithHints.put("apple", "A red or green fruit.");
    wordsWithHints.put("banana", "A yellow fruit that monkeys love.");
    wordsWithHints.put("cherry", "A small red fruit often used as a topping.");
    wordsWithHints.put("date", "A sweet fruit often found in deserts.");
    wordsWithHints.put("elderberry", "A dark purple berry used in syrups.");

    // Seleccionar una palabra aleatoria y su pista
    List<String> keys = new ArrayList<>(wordsWithHints.keySet());
    String randomWord = keys.get((int) (Math.random() * keys.size()));
    this.wordToGuess = randomWord;
    this.hint = wordsWithHints.get(randomWord);
  }

  // Métodos para agregar jugadores y trazos
  public void addPlayer(String player) {
    if (!players.contains(player)) {
      players.add(player);
    }
  }

  public void removePlayer(String player) {
    players.remove(player);
  }

  public void addTrace(Trace trace) {
    traces.add(trace);
  }

  // Getters y Setters
  public String getRoomId() {
    return roomId;
  }

  public List<String> getPlayers() {
    return players;
  }

  public List<Trace> getTraces() {
    return traces;
  }

  public String getWordToGuess() {
    return wordToGuess;
  }

  public String getHint() {
    return hint;
  }
   public boolean isClueAlreadyGiven() {
    return clueAlreadyGiven;
  }

  public void markClueAsGiven() {
    this.clueAlreadyGiven = true;
  }
}
