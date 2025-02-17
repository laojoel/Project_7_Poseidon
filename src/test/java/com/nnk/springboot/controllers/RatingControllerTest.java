package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.services.RatingService;
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

public class RatingControllerTest {

    private RatingService ratingService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        ratingService = Mockito.mock(RatingService.class);
        UserService userService = Mockito.mock(UserService.class);
        RatingController ratingController = new RatingController();
        ratingController.setRatingService(ratingService);
        ratingController.setUserService(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    public void testHome() throws Exception {
        List<Rating> ratings = new ArrayList<>();
        when(ratingService.loadAll()).thenReturn(ratings);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratings", ratings));
    }

    @Test
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        when(ratingService.loadAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fitchRating", "Test fitch")
                        .param("moodysRating", "Test moodys")
                        .param("orderNumber", "10")
                        .param("sandPRating", "test sand p"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    public void testValidateRating_Failure() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fitchRating", "")
                        .param("moodysRating", "")
                        .param("orderNumber", "0")
                        .param("sandPRating", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attributeHasFieldErrors("rating", "fitchRating"));
    }

    @Test
    public void testShowUpdateForm_Success() throws Exception {
        Rating rating = new Rating();
        when(ratingService.load(anyInt())).thenReturn(Optional.of(rating));

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", rating));
    }

    @Test
    public void testShowUpdateForm_Failure() throws Exception {
        when(ratingService.load(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    public void testUpdateRating_Success() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fitchRating", "Test fitch")
                        .param("moodysRating", "Test moodys")
                        .param("orderNumber", "10")
                        .param("sandPRating", "test sand p"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    public void testUpdateRating_Failure() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fitchRating", "")
                        .param("moodysRating", "")
                        .param("orderNumber", "0")
                        .param("sandPRating", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void testDeleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        Mockito.verify(ratingService).delete(1);
    }
}
