package com.aws.demo.springboot.web;

import com.aws.demo.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class,
excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void return_hello_isTrue() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello")).andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void return_hello_dto_test() throws Exception {
        String name = "name";
        int amount = 1000;
        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}