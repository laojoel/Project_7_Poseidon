package com.nnk.springboot.services;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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

public class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        CurvePoint curvePoint1 = new CurvePoint();
        CurvePoint curvePoint2 = new CurvePoint();
        List<CurvePoint> curvePoints = Arrays.asList(curvePoint1, curvePoint2);
        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        // Act
        List<CurvePoint> result = curvePointService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        int id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        // Act
        Optional<CurvePoint> result = curvePointService.load(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(curvePoint, result.get());
        verify(curvePointRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        // Arrange
        CurvePoint curvePoint = new CurvePoint();

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

