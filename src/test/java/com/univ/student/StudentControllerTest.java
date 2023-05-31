package com.univ.student;

import com.univ.student.controller.StudentController;
import com.univ.student.domain.model.StudentDto;
import com.univ.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;

    @Test
    public void GIVEN_that_student_record_created_WHEN_student_data_saved_returns_with_id() throws Exception {
        Mockito.when(studentService.createStudent(StudentDto.builder().firstName("Rajat").lastName("Gupta")
                .dob(LocalDate.of(2000,1,11))
                .admissionDate(null)
                .departmentName("Chemistry").courseName("Bachelor of Science").build())).thenReturn(
                StudentDto.builder().id(1l).firstName("Rajat").lastName("Gupta")
                        .dob(LocalDate.of(2000,1,11))
                        .admissionDate(null).registrationId(null)
                        .departmentName("Chemistry").courseName("Bachelor of Science").build());
        String postContentJson = "{\"firstName\":\"Rajat\",\"lastName\":\"Gupta\",\"dob\":\"2000-01-11\","
                + "\"admissionDate\":null,\"registrationId\":null,\"departmentName\":\"Chemistry\",\"courseName\":\"Bachelor of Science\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/student").accept(MediaType.APPLICATION_JSON)
                .content(postContentJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJsonResponse = "{\"id\":1,\"firstName\":\"Rajat\",\"lastName\":\"Gupta\",\"dob\":\"2000-01-11\","
                + "\"admissionDate\":null,\"registrationId\":null,\"departmentName\":\"Chemistry\",\"courseName\":\"Bachelor of Science\"}";

        Assertions.assertEquals(200, result.getResponse().getStatus());
        log.info("Result:"+result.getResponse().getContentAsString());
        Assertions.assertEquals(expectedJsonResponse, result.getResponse().getContentAsString());
    }

    @Test
    public void GIVEN_that_student_record_fetched_with_id_WHEN_retrieved_json_result_should_match() throws Exception {
        Mockito.when(studentService.getStudent(1L)).thenReturn(
                StudentDto.builder().id(1l).firstName("Rajat").lastName("Gupta")
                        .dob(LocalDate.of(2000,1,11))
                        .admissionDate(null).registrationId(null)
                        .departmentName("Chemistry").courseName("Bachelor of Science").build());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/student/id/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expectedJsonResponse = "{\"id\":1,\"firstName\":\"Rajat\",\"lastName\":\"Gupta\",\"dob\":\"2000-01-11\","
                + "\"admissionDate\":null,\"registrationId\":null,\"departmentName\":\"Chemistry\",\"courseName\":\"Bachelor of Science\"}";

        log.info("Result:"+result.getResponse().getContentAsString());
        Assertions.assertEquals(expectedJsonResponse, result.getResponse().getContentAsString());
    }

    @Test
    public void GIVEN_that_all_student_records_fetched_WHEN_retrieved_record_count_should_match() throws Exception {
        Mockito.when(studentService.getStudentList()).thenReturn(List.of(
                StudentDto.builder().id(1l).firstName("Rajat").lastName("Gupta")
                        .dob(LocalDate.of(2000,1,11))
                        .admissionDate(null)
                        .departmentName("Chemistry").courseName("Bachelor of Science").build()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/student").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        log.info("Result:"+result.getResponse().getContentAsString());
        Assertions.assertEquals(1, new JSONArray(result.getResponse().getContentAsString()).length());
    }

    @Test
    public void GIVEN_that_all_student_records_fetched_WHEN_requested_by_department_name_retrieved_record_count_should_match() throws Exception {
        Mockito.when(studentService.getStudentByDepartmentName("Chemistry")).thenReturn(List.of(
                StudentDto.builder().id(1l).firstName("Rajat").lastName("Gupta")
                        .dob(LocalDate.of(2000,1,11))
                        .admissionDate(null)
                        .departmentName("Chemistry").courseName("Bachelor of Science").build()));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/student/departmentName/Chemistry").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        log.info("Result:"+result.getResponse().getContentAsString());
        Assertions.assertEquals(1, new JSONArray(result.getResponse().getContentAsString()).length());
    }
}
