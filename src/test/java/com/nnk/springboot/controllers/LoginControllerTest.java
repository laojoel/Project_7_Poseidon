package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class LoginControllerTest {

    private MockMvc mockMvc;
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);

        LoginController loginController = new LoginController();
        loginController.setUserRepository(userRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/app/login"))
                .andExpect(view().name("appLogin"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeDoesNotExist("message"));
    }

    @Test
    public void testLoginPage_Error() throws Exception {
        mockMvc.perform(get("/app/login?error=true"))
                .andExpect(view().name("appLogin"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void testLoginPage_Logout() throws Exception {
        mockMvc.perform(get("/app/login?logout=true"))
                .andExpect(view().name("appLogin"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void testPerformLogin() throws Exception {
        mockMvc.perform(post("/app/perform-login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("appLogin"));
    }

    @Test
    public void testGetAllUserArticles() throws Exception {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void testErrorPage() throws Exception {
        mockMvc.perform(get("/app/error"))
                .andExpect(view().name("403"))
                .andExpect(model().attributeExists("errorMsg"));
    }
}
