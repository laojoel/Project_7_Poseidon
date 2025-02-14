package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Curve;
import com.nnk.springboot.services.CurveService;
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

public class CurveControllerTest {

    private CurveService curveService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        curveService = Mockito.mock(CurveService.class);
        CurveController curveController = new CurveController();
        curveController.setCurveService(curveService);
        mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();
    }

    @Test
    public void testHome() throws Exception {
        List<Curve> curves = new ArrayList<>();
        when(curveService.loadAll()).thenReturn(curves);

        mockMvc.perform(get("/curve/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/list"))
                .andExpect(model().attribute("curves", curves));
    }

    @Test
    public void testAddCurveForm() throws Exception {
        mockMvc.perform(get("/curve/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/add"))
                .andExpect(model().attributeExists("curve"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        when(curveService.loadAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/curve/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("term", "20.8")
                        .param("value", "10.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/list"))
                .andExpect(model().attributeExists("curves"));
    }

    @Test
    public void testValidateCurve_Failure() throws Exception {
        mockMvc.perform(post("/curve/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/add"))
                .andExpect(model().attributeExists("curve"))
                .andExpect(model().attributeHasFieldErrors("curve", "term"));
    }

    @Test
    public void testShowUpdateForm_Success() throws Exception {
        Curve curve = new Curve();
        when(curveService.load(anyInt())).thenReturn(Optional.of(curve));

        mockMvc.perform(get("/curve/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/update"))
                .andExpect(model().attribute("curve", curve));
    }

    @Test
    public void testShowUpdateForm_Failure() throws Exception {
        when(curveService.load(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/curve/update/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curve/list"));
    }

    @Test
    public void testUpdateCurve_Success() throws Exception {
        mockMvc.perform(post("/curve/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("term", "20.8")
                        .param("value", "10.5"))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/list"));
    }

    @Test
    public void testUpdateCurve_Failure() throws Exception {
        mockMvc.perform(post("/curve/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("term", "")
                        .param("value", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("curve/update"));
    }

    @Test
    public void testDeleteCurve() throws Exception {
        mockMvc.perform(get("/curve/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curve/list"));

        Mockito.verify(curveService).delete(1);
    }
}
