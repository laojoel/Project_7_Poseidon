package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.services.BidService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BidControllerTest {

    private BidService bidService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        bidService = Mockito.mock(BidService.class);
        BidController bidController = new BidController();
        bidController.setBidService(bidService);
        mockMvc = MockMvcBuilders.standaloneSetup(bidController).build();
    }

    @Test
    public void testHome() throws Exception {
        List<Bid> bids = new ArrayList<>();
        when(bidService.loadAll()).thenReturn(bids);

        mockMvc.perform(get("/bid/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/list"))
                .andExpect(model().attribute("bids", bids));
    }

    @Test
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bid/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/add"))
                .andExpect(model().attributeExists("bid"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        when(bidService.loadAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/bid/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/list"))
                .andExpect(model().attributeExists("bids"));
    }

    @Test
    public void testValidateBid_Failure() throws Exception {
        mockMvc.perform(post("/bid/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/add"))
                .andExpect(model().attributeExists("bid"))
                .andExpect(model().attributeHasFieldErrors("bid", "account"));
    }

    @Test
    public void testShowUpdateForm_Success() throws Exception {
        Bid bid = new Bid();
        when(bidService.load(anyInt())).thenReturn(Optional.of(bid));

        mockMvc.perform(get("/bid/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/update"))
                .andExpect(model().attribute("bid", bid));
    }

    @Test
    public void testShowUpdateForm_Failure() throws Exception {
        when(bidService.load(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/bid/update/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bid/list"));
    }

    @Test
    public void testUpdateBid_Success() throws Exception {
        mockMvc.perform(post("/bid/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "test account")
                        .param("type", "test password")
                        .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/list"));
    }

    @Test
    public void testUpdateBid_Failure() throws Exception {
        mockMvc.perform(post("/bid/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "")
                        .param("quantity", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bid/update"));
    }

    @Test
    public void testDeleteBid() throws Exception {
        mockMvc.perform(get("/bid/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bid/list"));

        Mockito.verify(bidService).delete(1);
    }
}
