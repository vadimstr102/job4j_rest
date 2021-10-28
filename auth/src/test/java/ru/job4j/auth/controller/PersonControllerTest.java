package ru.job4j.auth.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {
    @MockBean
    private PersonRepository persons;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenFindAll() throws Exception {
        when(persons.findAll()).thenReturn(Arrays.asList(new Person(), new Person(), new Person()));
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void whenFindById() throws Exception {
        Person person = new Person("Ivan", "ivan@password");
        person.setId(777);
        when(persons.findById(777)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/777"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(777)))
                .andExpect(jsonPath("$.login", equalTo("Ivan")))
                .andExpect(jsonPath("$.password", equalTo("ivan@password")));
    }

    @Test
    public void whenNotFoundById() throws Exception {
        mockMvc.perform(get("/person/666"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id", equalTo(0)))
                .andExpect(jsonPath("$.login", equalTo(null)))
                .andExpect(jsonPath("$.password", equalTo(null)));
    }

    @Test
    public void whenCreate() throws Exception {
        Gson gson = new Gson();
        Person person1 = new Person("Ivan", "ivan@password");
        Person person2 = new Person("Ivan", "ivan@password");
        person2.setId(777);
        when(persons.save(person1)).thenReturn(person2);
        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(person1)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", equalTo(777)))
                .andExpect(jsonPath("$.login", equalTo("Ivan")))
                .andExpect(jsonPath("$.password", equalTo("ivan@password")));
    }

    @Test
    public void whenUpdate() throws Exception {
        Gson gson = new Gson();
        Person person = new Person("Ivan", "ivan@password");
        person.setId(777);
        mockMvc.perform(put("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(person)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete() throws Exception {
        mockMvc.perform(delete("/person/777"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
