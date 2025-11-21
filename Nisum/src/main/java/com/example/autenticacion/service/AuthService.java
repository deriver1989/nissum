package com.example.autenticacion.service;


import com.example.autenticacion.entities.Phones;
import com.example.autenticacion.entities.Token;
import com.example.autenticacion.entities.User;
import com.example.autenticacion.reporitories.PhonesRepository;
import com.example.autenticacion.reporitories.TokenRepository;
import com.example.autenticacion.reporitories.UserRepository;
import com.example.autenticacion.request.AuthRequest;
import com.example.autenticacion.request.RegisterRequest;
import com.example.autenticacion.response.Mensajes;
import com.example.autenticacion.response.RegistroUsuarioResponseDTO;
import com.example.autenticacion.response.TokenResponse;
import com.example.autenticacion.response.UserResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PhonesRepository phonesRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegistroUsuarioResponseDTO register(final RegisterRequest request) throws Exception {
        if(repository.findByEmail(request.email()).orElse(null) != null){
            throw new Exception(Mensajes.CORREO_REGISTRADO);
        }
        List<Phones> phones = request.phones().stream()
                .map(p -> Phones.builder()
                        .number(p.number())
                        .citycode(p.citycode())
                        .contrycode(p.contrycode())
                        .build()
                ).toList();

        final User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(request.rol())
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .last_login(LocalDateTime.now())
                .isactive(Boolean.TRUE)
                .build();

        phones.forEach(phone -> phone.setUser(user));
        user.setPhones(phones);

        //Guardar usuario
        final User savedUser = repository.save(user);

        //Guardar telefonos
        //final List<Phones> phones = phonesRepository.saveAll(phonesDTO);

        final String jwtToken = jwtService.generateToken(savedUser);
        final String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);

        RegistroUsuarioResponseDTO respuesta = new RegistroUsuarioResponseDTO();
        respuesta.setToken(new TokenResponse(jwtToken, refreshToken));
        respuesta.setUsuario(toResponse(user));
        respuesta.setMensaje(Mensajes.REGISTRO_OK);
        return respuesta;
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        response.setRol(user.getRol());
        response.setCreated(user.getCreated());
        response.setModified(user.getModified());
        response.setLast_login(user.getLast_login());
        response.setIsactive(user.getIsactive());

        // Clona la lista de phones (evita exponer la entidad original)
        if (user.getPhones() != null) {
            response.setPhones(
                    user.getPhones()
                            .stream()
                            .map(p -> new Phones(
                                    p.getId(),
                                    p.getNumber(),
                                    p.getCitycode(),
                                    p.getContrycode()
                            ))
                            .toList()
            );
        }

        return response;
    }

    public TokenResponse authenticate(final AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final User user = repository.findByEmail(request.email())
                .orElseThrow();
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    public TokenResponse refreshToken(@NotNull final String authentication) {

        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = authentication.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            return null;
        }

        final User user = this.repository.findByEmail(userEmail).orElseThrow();
        final boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            return null;
        }

        final String accessToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }
}
