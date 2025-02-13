package com.nnk.springboot.services;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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

public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        List<Rating> ratings = Arrays.asList(rating1, rating2);
        when(ratingRepository.findAll()).thenReturn(ratings);

        // Act
        List<Rating> result = ratingService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        int id = 1;
        Rating rating = new Rating();
        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        // Act
        Optional<Rating> result = ratingService.load(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(rating, result.get());
        verify(ratingRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        // Arrange
        Rating rating = new Rating();

        // Act
        ratingService.save(rating);

        // Assert
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void testDelete() {
        // Arrange
        int id = 1;

        // Act
        ratingService.delete(id);

        // Assert
        verify(ratingRepository, times(1)).deleteById(id);
    }
}