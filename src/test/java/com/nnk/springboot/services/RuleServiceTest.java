package com.nnk.springboot.services;

import com.nnk.springboot.domains.Rule;
import com.nnk.springboot.repositories.RuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RuleServiceTest {

    @Mock
    private RuleRepository ruleRepository;

    @InjectMocks
    private RuleService ruleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        Rule rule1 = new Rule();
        Rule rule2 = new Rule();
        List<Rule> rules = Arrays.asList(rule1, rule2);
        when(ruleRepository.findAll()).thenReturn(rules);

        // Act
        List<Rule> result = ruleService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(ruleRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        int id = 1;
        Rule rule = new Rule();
        when(ruleRepository.findById(id)).thenReturn(Optional.of(rule));

        // Act
        Optional<Rule> result = ruleService.load(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(rule, result.get());
        verify(ruleRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        // Arrange
        Rule rule = new Rule();

        // Act
        ruleService.save(rule);

        // Assert
        verify(ruleRepository, times(1)).save(rule);
    }

    @Test
    public void testDelete() {
        // Arrange
        int id = 1;

        // Act
        ruleService.delete(id);

        // Assert
        verify(ruleRepository, times(1)).deleteById(id);
    }
}

