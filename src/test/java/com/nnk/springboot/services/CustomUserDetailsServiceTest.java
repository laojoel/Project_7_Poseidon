package com.nnk.springboot.services;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameTest_UserExists() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setRole("user");
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        // Assert
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void loadUserByUsernameTest_UserDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));

        // Assert
        verify(userRepository, times(1)).findByUsername(username);
    }
}
