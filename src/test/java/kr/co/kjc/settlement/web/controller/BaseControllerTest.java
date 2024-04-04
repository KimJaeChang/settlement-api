package kr.co.kjc.settlement.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class BaseControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  Gson gson;

  @Test
  @DisplayName("health-check 테스트")
//  @Transactional
    // @Rollback(value = false)
  void test() throws Exception {
    // given

    /// when
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .get("/health-check")
            .content("")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(Charset.forName("UTF-8"))
        )
        .andExpect(status().isOk())
        .andReturn();

    // then
    Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo("success");
//    assertThrows(IllegalStateException.class, () -> {
//      System.out.println(); // 예외가 발생해야 한다.
//    });
  }


}