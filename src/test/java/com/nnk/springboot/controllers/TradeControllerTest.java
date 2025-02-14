package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.services.TradeService;
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

public class TradeControllerTest {

    private TradeService tradeService;
    private MockMvc mockMvc;
    
    @BeforeEach
    public void setUp() {
        tradeService = Mockito.mock(TradeService.class);
        TradeController tradeController = new TradeController();
        tradeController.setTradeService(tradeService);
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    public void testHome() throws Exception {
        List<Trade> trades = new ArrayList<>();
        when(tradeService.loadAll()).thenReturn(trades);

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attribute("trades", trades));
    }

    @Test
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        when(tradeService.loadAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    public void testValidateTrade_Failure() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attributeHasFieldErrors("trade", "account"));
    }

    @Test
    public void testShowUpdateForm_Success() throws Exception {
        Trade trade = new Trade();
        when(tradeService.load(anyInt())).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attribute("trade", trade));
    }

    @Test
    public void testShowUpdateForm_Failure() throws Exception {
        when(tradeService.load(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    public void testUpdateTrade_Success() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "test account")
                        .param("type", "test password")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    public void testUpdateTrade_Failure() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void testDeleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));

        Mockito.verify(tradeService).delete(1);
    }
}
