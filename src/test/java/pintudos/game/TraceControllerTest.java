package pintudos.game;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pintudos.game.controller.TraceController;
import pintudos.game.model.CustomPoint;
import pintudos.game.model.Trace;
import pintudos.game.service.TraceService;

@WebMvcTest(TraceController.class)
public class TraceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TraceService traceService;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void saveTrace_returnsSavedTrace() throws Exception {
    CustomPoint point1 = new CustomPoint(10.0, 20.0);
    CustomPoint point2 = new CustomPoint(30.0, 40.0);

    Trace traceToSave = new Trace(
      "room1",
      Arrays.asList(point1, point2),
      "red",
      3
    );
    Trace savedTrace = new Trace(
      "room1",
      Arrays.asList(point1, point2),
      "red",
      3
    );
    savedTrace.setId("123");

    when(traceService.saveTrace(any(Trace.class))).thenReturn(savedTrace);

    // Construir DTO JSON con los datos para enviar
    String jsonRequest =
      """
      {
        "roomId": "room1",
        "points": [
          {"latitude": 10.0, "longitude": 20.0},
          {"latitude": 30.0, "longitude": 40.0}
        ],
        "color": "red",
        "width": 3
      }
      """;

    mockMvc
      .perform(
        post("/traces")
          .contentType(MediaType.APPLICATION_JSON)
          .content(jsonRequest)
      )
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.roomId").value("room1"))
      .andExpect(jsonPath("$.color").value("red"))
      .andExpect(jsonPath("$.width").value(3))
      .andExpect(jsonPath("$.id").value("123"));

    verify(traceService).saveTrace(any(Trace.class));
  }

  @Test
  public void getTraces_returnsListOfTraces() throws Exception {
    CustomPoint point1 = new CustomPoint(10.0, 20.0);
    Trace trace1 = new Trace("room1", Arrays.asList(point1), "blue", 2);
    trace1.setId("t1");

    CustomPoint point2 = new CustomPoint(30.0, 40.0);
    Trace trace2 = new Trace("room1", Arrays.asList(point2), "green", 4);
    trace2.setId("t2");

    when(traceService.getTracesByRoom("room1"))
      .thenReturn(Arrays.asList(trace1, trace2));

    mockMvc
      .perform(get("/traces/room1"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.length()").value(2))
      .andExpect(jsonPath("$[0].id").value("t1"))
      .andExpect(jsonPath("$[1].id").value("t2"))
      .andExpect(jsonPath("$[0].color").value("blue"))
      .andExpect(jsonPath("$[1].color").value("green"));

    verify(traceService).getTracesByRoom("room1");
  }
}
