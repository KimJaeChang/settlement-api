package kr.co.kjc.settlement.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import kr.co.kjc.settlement.controller.CommonCodeApiController;
import kr.co.kjc.settlement.global.dtos.request.BaseSearchDTO;
import kr.co.kjc.settlement.global.dtos.response.BaseResponseDTO;
import kr.co.kjc.settlement.service.CommonCodeService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Slf4j
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
class CommonCodeServiceImplTest {

  @Mock
  private CommonCodeService commonCodeService;

  @InjectMocks
  CommonCodeApiController commonCodeApiController;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper om;

  private <T> T getResult(MvcResult mvcResult, Class<T> cl)
      throws UnsupportedEncodingException, JsonProcessingException {

    BaseResponseDTO<?> baseResponseDTO = om.convertValue(
        mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8),
        BaseResponseDTO.class);

    String body = om.writeValueAsString(baseResponseDTO.getBody());
    return om.convertValue(body, cl);
  }

  @BeforeEach
  void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(commonCodeApiController).build();
  }

  @Test
  @DisplayName("공통코드 전체조회 Test")
    //@Rollback(value = false)
  void findAllTest() throws Exception {
    // given

    // when
    ResultActions perform = mockMvc.perform(
        MockMvcRequestBuilders.get("/api/v1/common-codes") // uri 주소
            .content(om.writeValueAsString(new BaseSearchDTO(0, 10)))
            .header("Authorization", "")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(StandardCharsets.UTF_8)
    );

    // then
    ResultActions resultActions = perform.andExpect(status().isOk());

    Page<?> result = om.convertValue(
        resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
        Page.class);

    Assertions.assertThat(result.getContent().size()).isGreaterThan(0);
  }

}