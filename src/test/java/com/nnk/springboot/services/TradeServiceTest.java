package com.nnk.springboot.services;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;
    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        List<Trade> trades = new ArrayList<>();
        trades.add(new Trade());
        trades.add(new Trade());
        when(tradeRepository.findAll()).thenReturn(trades);

        // Act
        List<Trade> result = tradeService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        Trade trade = new Trade();
        trade.setId((long)1);
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        // Act
        Optional<Trade> result = tradeService.load(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(trade, result.get());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Arrange
        Trade trade = new Trade();

        // Act
        tradeService.save(trade);

        // Assert
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    public void testDelete() {
        // Arrange
        int tradeId = 1;

        // Act
        tradeService.delete(tradeId);

        // Assert
        verify(tradeRepository, times(1)).deleteById(tradeId);
    }
}
