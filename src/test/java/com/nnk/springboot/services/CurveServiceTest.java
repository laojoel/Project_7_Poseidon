package com.nnk.springboot.services;

import com.nnk.springboot.domains.Curve;
import com.nnk.springboot.repositories.CurveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurveServiceTest {

    @Mock
    private CurveRepository curvePointRepository;

    @InjectMocks
    private CurveService curvePointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        Curve curvePoint1 = new Curve();
        Curve curvePoint2 = new Curve();
        List<Curve> curvePoints = Arrays.asList(curvePoint1, curvePoint2);
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        // Act
        List<Curve> result = curvePointService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        int id = 1;
        Curve curvePoint = new Curve();
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        // Act
        Optional<Curve> result = curvePointService.load(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(curvePoint, result.get());
        verify(curvePointRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        // Arrange
        Curve curvePoint = new Curve();

        // Act
        curvePointService.save(curvePoint);

        // Assert
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    public void testDelete() {
        // Arrange
        int id = 1;

        // Act
        curvePointService.delete(id);

        // Assert
        verify(curvePointRepository, times(1)).deleteById(id);
    }
}

