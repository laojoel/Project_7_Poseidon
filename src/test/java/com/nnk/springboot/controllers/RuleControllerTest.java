package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Rule;
import com.nnk.springboot.services.RuleService;
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

public class RuleControllerTest {

    private RuleService ruleService;
    private MockMvc mockMvc;
    
    @BeforeEach
    public void setUp() {
        ruleService = Mockito.mock(RuleService.class);
        RuleController ruleController = new RuleController();
        ruleController.setRuleService(ruleService);
        mockMvc = MockMvcBuilders.standaloneSetup(ruleController).build();
    }

    @Test
    public void testHome() throws Exception {
        List<Rule> rules = new ArrayList<>();
        when(ruleService.loadAll()).thenReturn(rules);

        mockMvc.perform(get("/rule/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/list"))
                .andExpect(model().attribute("rules", rules));
    }

    @Test
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(get("/rule/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/add"))
                .andExpect(model().attributeExists("rule"));
    }

    @Test
    public void testValidate_Success() throws Exception {
        when(ruleService.loadAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/rule/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Test account")
                        .param("description", "Test description")
                        .param("json", "Test Json")
                        .param("template", "Test template")
                        .param("sqlStr", "Test SQL")
                        .param("sqlPart", "Test SQL Part"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/list"))
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    public void testValidateRule_Failure() throws Exception {
        mockMvc.perform(post("/rule/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/add"))
                .andExpect(model().attributeExists("rule"))
                .andExpect(model().attributeHasFieldErrors("rule", "name"));
    }

    @Test
    public void testShowUpdateForm_Success() throws Exception {
        Rule rule = new Rule();
        when(ruleService.load(anyInt())).thenReturn(Optional.of(rule));

        mockMvc.perform(get("/rule/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/update"))
                .andExpect(model().attribute("rule", rule));
    }

    @Test
    public void testShowUpdateForm_Failure() throws Exception {
        when(ruleService.load(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(get("/rule/update/1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rule/list"));
    }

    @Test
    public void testUpdateRule_Success() throws Exception {
        mockMvc.perform(post("/rule/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "Test account")
                        .param("description", "Test description")
                        .param("json", "Test Json")
                        .param("template", "Test template")
                        .param("sqlStr", "Test SQL")
                        .param("sqlPart", "Test SQL Part"))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/list"));
    }

    @Test
    public void testUpdateRule_Failure() throws Exception {
        mockMvc.perform(post("/rule/update/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "")
                        .param("description", "")
                        .param("json", "")
                        .param("template", "")
                        .param("sqlStr", "")
                        .param("sqlPart", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("rule/update"));
    }

    @Test
    public void testDeleteRule() throws Exception {
        mockMvc.perform(get("/rule/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rule/list"));

        Mockito.verify(ruleService).delete(1);
    }
}
