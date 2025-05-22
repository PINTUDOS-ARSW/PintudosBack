package pintudos.game;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pintudos.game.model.Trace;
import pintudos.game.repository.TraceRepository;
import pintudos.game.service.TraceService;

public class TraceServiceTest {

  @Mock
  private TraceRepository traceRepository;

  @InjectMocks
  private TraceService traceService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void saveTrace_callsRepositorySave() {
    Trace trace = new Trace("room1", null, "red", 3);
    when(traceRepository.save(trace)).thenReturn(trace);

    Trace saved = traceService.saveTrace(trace);

    verify(traceRepository).save(trace);
    assertThat(saved).isEqualTo(trace);
  }

  @Test
  public void getTracesByRoom_callsRepositoryFindByRoomId() {
    String roomId = "room1";
    List<Trace> traces = Arrays.asList(
      new Trace(roomId, null, "blue", 2),
      new Trace(roomId, null, "green", 4)
    );

    when(traceRepository.findByRoomId(roomId)).thenReturn(traces);

    List<Trace> result = traceService.getTracesByRoom(roomId);

    verify(traceRepository).findByRoomId(roomId);
    assertThat(result).isEqualTo(traces);
  }
}
