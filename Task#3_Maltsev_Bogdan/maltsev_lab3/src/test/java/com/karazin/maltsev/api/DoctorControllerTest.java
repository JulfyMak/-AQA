package com.karazin.maltsev.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karazin.maltsev.config.ReplaceCamelCase;
import com.karazin.maltsev.model.DoctorRequest;
import com.karazin.maltsev.utils.TestConditions;
import com.karazin.maltsev.entity.Doctor;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(ReplaceCamelCase.class)
public class DoctorControllerTest {

  private static final String DOCTORS_PATH = "/doctors/";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  void shouldFetchAllDoctors() throws Exception {
    // when
    MvcResult mvcResult = performRequest(DOCTORS_PATH);

    List<Doctor> doctors = mapper.readValue(mvcResult.getResponse().getContentAsString(),
        List.class);

    // then
    assertThat(doctors)
        .isNotEmpty()
        .hasSize(2);
  }

  @Test
  void shouldFetchDoctorById() throws Exception {
    // when
    MvcResult mvcResult = performRequest(DOCTORS_PATH + "1");
    Doctor doctor = mapper.readValue(mvcResult.getResponse().getContentAsString(), Doctor.class);

    // then
    assertThat(doctor)
        .isNotNull()
        .hasNoNullFieldsOrProperties()
        .has(TestConditions.DOCTOR_NAME_IS_BOGDAN);
  }

  @Test
  void shouldFetchDoctorsByFirstName() throws Exception {
    // given
    String requestedName = "Bogdan";
    DoctorRequest doctorRequest = DoctorRequest.builder()
        .firstName(requestedName)
        .build();

    // when
    MvcResult mvcResult = performRequest(DOCTORS_PATH, doctorRequest);
    List<Doctor> doctorsByName = mapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<>() {
        });

    // then
    assertThat(doctorsByName)
        .isNotEmpty()
        .has(TestConditions.DOCTORS_NAMES_ARE_BOGDAN);
  }

  @Test
  void shouldThrowExceptionWhenFetchingByFirstName() throws Exception {
    // given
    String requestedName = "bogdan";
    DoctorRequest doctorRequest = DoctorRequest.builder()
        .firstName(requestedName)
        .build();

    // when
    MvcResult mvcResult = performRequest(DOCTORS_PATH, doctorRequest);

    // then
    assertThat(mvcResult.getResolvedException())
        .isInstanceOf(IllegalArgumentException.class);
  }

  private MvcResult performRequest(String path) throws Exception {
    return mockMvc.perform(get(path))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andReturn();
  }

  private MvcResult performRequest(String path, DoctorRequest request)
      throws Exception {
    return mockMvc.perform(post(path)
            .content(mapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();
  }
}
