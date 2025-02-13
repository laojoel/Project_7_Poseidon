package com.nnk.springboot.services;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.repositories.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BidServiceTest {

    @InjectMocks
    private BidService bidService;
    @Mock
    private BidRepository bidRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid());
        bids.add(new Bid());
        when(bidRepository.findAll()).thenReturn(bids);

        // Act
        List<Bid> result = bidService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(bidRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        Bid bid = new Bid();
        bid.setId((long)1);
        when(bidRepository.findById(1)).thenReturn(Optional.of(bid));

        // Act
        Optional<Bid> result = bidService.load(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(bid, result.get());
        verify(bidRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Arrange
        Bid bid = new Bid();

        // Act
        bidService.save(bid);

        // Assert
        verify(bidRepository, times(1)).save(bid);
    }

    @Test
    public void testDelete() {
        // Arrange
        int bidId = 1;

        // Act
        bidService.delete(bidId);

        // Assert
        verify(bidRepository, times(1)).deleteById(bidId);
    }
}
