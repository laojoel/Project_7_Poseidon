package com.nnk.springboot.services;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAll() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.loadAll();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testLoad() {
        // Arrange
        User user = new User();
        user.setId((long)1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.load(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        // Arrange
        User user = new User();

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDelete() {
        // Arrange
        int userId = 1;

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testGetRemoteRole_ValidRole() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getAuthorities()).thenReturn(new ArrayList<>());

        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return null;
            }
            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserService userService = new UserService();

        // Act
        String role = userService.getRemoteRole();

        // Assert
        assertNotNull(role);
    }
}
