package com.example.autenticacion.service;

import com.example.autenticacion.entities.Token;
import com.example.autenticacion.entities.User;
import com.example.autenticacion.reporitories.PhonesRepository;
import com.example.autenticacion.reporitories.TokenRepository;
import com.example.autenticacion.reporitories.UserRepository;
import com.example.autenticacion.request.PhonesRequest;
import com.example.autenticacion.request.RegisterRequest;
import com.example.autenticacion.response.Mensajes;
import com.example.autenticacion.response.RegistroUsuarioResponseDTO;
import com.example.autenticacion.response.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhonesRepository phonesRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_DeberiaRegistrarUsuarioCorrectamente() throws Exception {

        RegisterRequest request = new RegisterRequest(
                "Juan",
                "juan@example.com",
                "Password123",
                "ADMIN",
                List.of(new PhonesRequest("12345", "1", "57"))
        );

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.password()))
                .thenReturn("ENCRYPTED_PASS");

        User savedUser = User.builder()
                .id(1)
                .name("Juan")
                .email("juan@example.com")
                .password("ENCRYPTED_PASS")
                .rol("ADMIN")
                .phones(List.of())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .last_login(LocalDateTime.now())
                .isactive(true)
                .build();

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        when(jwtService.generateToken(savedUser)).thenReturn("ACCESS_TOKEN");
        when(jwtService.generateRefreshToken(savedUser)).thenReturn("REFRESH_TOKEN");

        // Act
        RegistroUsuarioResponseDTO response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals(Mensajes.REGISTRO_OK, response.getMensaje());

        TokenResponse tokenResponse = response.getToken();
        assertEquals("ACCESS_TOKEN", tokenResponse.accessToken());
        assertEquals("REFRESH_TOKEN", tokenResponse.refreshToken());

        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void register_DeberiaLanzarErrorCuandoCorreoExiste() {

        RegisterRequest request = new RegisterRequest(
                "Juan",
                "juan@example.com",
                "Password123",
                "ADMIN",
                List.of()
        );

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(new User()));

        Exception ex = assertThrows(Exception.class, () -> authService.register(request));
        assertEquals(Mensajes.CORREO_REGISTRADO, ex.getMessage());
    }
}