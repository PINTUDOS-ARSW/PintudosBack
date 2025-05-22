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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pintudos.game.controller.TraceController;
import pintudos.game.model.CustomPoint;
import pintudos.game.model.Trace;
import pintudos.game.service.TraceService;

@AutoConfigureMockMvc(addFilters = false)
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
