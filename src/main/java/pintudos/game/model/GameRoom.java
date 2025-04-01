package pintudos.game.model;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {

  private String roomId;
  private List<String> players; // Listado de jugadores conectados
  private List<Trace> traces; // Lista de trazos realizados

  public GameRoom(String roomId) {
    this.roomId = roomId;
    this.players = new ArrayList<>();
    this.traces = new ArrayList<>();
  }

  // Métodos para agregar jugadores y trazos
  public void addPlayer(String player) {
    players.add(player);
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
}
