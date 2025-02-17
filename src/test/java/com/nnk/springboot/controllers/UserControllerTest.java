package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        UserController userController = new UserController();
        userController.setUserService(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testHome() throws Exception {
        List<User> users = new ArrayList<>();
        when(userService.loadAll()).thenReturn(users);

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attribute("users", users));
    }

    @Test
    public void testAddUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        when(userService.loadAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "Test username")
                        .param("password", "Test password")
                        .param("fullname", "Test fullname")
                        .param("role", "admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    public void testValidateUser_Failure() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "")
                        .param("password", "")
                        .param("fullname", "")
                        .param("role", "admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("user", "username"));
    }

    @Test
    public void testShowUpdateForm_Success() throws Exception {
        User user = new User();
        when(userService.load(anyInt())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", user));
    }

    @Test
    public void testShowUpdateForm_Failure() throws Exception {
        when(userService.load(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    public void testUpdateUser_Success() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "Test username")
                        .param("password", "Test password")
                        .param("fullname", "Test fullname")
                        .param("role", "admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/list"));
    }

    @Test
    public void testUpdateUser_Failure() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
        Mockito.verify(userService).delete(1);
    }
}
