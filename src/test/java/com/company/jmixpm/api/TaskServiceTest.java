package com.company.jmixpm.api;

import com.company.jmixpm.JmixPmApplication;
import com.company.jmixpm.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URI;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = JmixPmApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class TaskServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private final RestTemplateBuilder builder = new RestTemplateBuilder();

    private String getAccessToken() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", "admin");
        params.add("password", "admin");

        String encoding = Base64.getEncoder().encodeToString("client:secret".getBytes());

        String resultString = mockMvc.perform(post(URI.create(
                        "http://localhost:8080/oauth/token"
                ))
                        .header("Authorization", "Basic " + encoding)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void getLeastBusyUserFromTaskService() throws Exception {
        User user = builder.defaultHeader("Authorization", "Bearer " + getAccessToken())
                .build()
                .getForEntity(
                        "http://localhost:8080/rest/services/jmixpm_TaskService/findLeastBusyUser",
                        User.class)
                .getBody();

        Assertions.assertEquals("admin", user.getUsername());
    }
}
