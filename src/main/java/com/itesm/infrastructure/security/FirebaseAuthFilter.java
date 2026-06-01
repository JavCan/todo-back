package com.itesm.infrastructure.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.itesm.application.security.AuthenticatedUserContext;
import com.itesm.application.security.CurrentUser;
import com.itesm.domain.models.User;
import com.itesm.domain.repository.UserRepository;
import com.itesm.infrastructure.firebase.FirebaseConfig;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import io.quarkus.arc.profile.UnlessBuildProfile;
import java.io.IOException;
import java.util.Optional;

@Provider
@Priority(Priorities.AUTHENTICATION)
@UnlessBuildProfile("test")
public class FirebaseAuthFilter implements ContainerRequestFilter {
    @Inject
    UserRepository userRepository;
    @Inject
    AuthenticatedUserContext authenticatedUserContext;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        if(path.equals("/user") || path.equals("user")
                || path.equals("/status") || path.equals("status")){
            return;
        }
        if(requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        String authHeader = requestContext.getHeaders().getFirst("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            requestContext.abortWith(
                    Response.status(401).build()
            );
            return;
        }
        try {
            assert authHeader != null;
            String token = authHeader.replace("Bearer ", "");
            String firebaseUuid = null;
            String email = null;
            String fullName = null;

            if (com.google.firebase.FirebaseApp.getApps().isEmpty()) {
                // Si Firebase no está inicializado (por falta de credenciales locales)
                // y estamos en modo de desarrollo, decodificamos el JWT sin verificar la firma.
                if (io.quarkus.runtime.LaunchMode.current() == io.quarkus.runtime.LaunchMode.DEVELOPMENT) {
                    System.out.println("WARN: Firebase no está inicializado. Decodificando token sin verificar firma (Modo Dev)...");
                    String[] parts = token.split("\\.");
                    if (parts.length >= 2) {
                        try {
                            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
                            firebaseUuid = getJsonField(payload, "sub");
                            email = getJsonField(payload, "email");
                            fullName = getJsonField(payload, "name");
                            if (firebaseUuid == null) {
                                firebaseUuid = getJsonField(payload, "user_id");
                            }
                        } catch (Exception ex) {
                            System.out.println("ERROR: No se pudo decodificar el token JWT. Usando valores de prueba.");
                        }
                    }
                    if (firebaseUuid == null) {
                        firebaseUuid = "mock-firebase-uid";
                        email = "dev-user@example.com";
                        fullName = "Dev User";
                    }
                } else {
                    throw new IllegalStateException("FirebaseApp con nombre [DEFAULT] no existe.");
                }
            } else {
                FirebaseToken decodedToken = com.google.firebase.auth.FirebaseAuth.getInstance().verifyIdToken(token, true);
                firebaseUuid = decodedToken.getUid();
                email = decodedToken.getEmail();
                fullName = (String) decodedToken.getClaims().get("name");
            }

            Optional<User> userOptional = userRepository.findByFirebaseUuid(firebaseUuid);
            User user;
            if (userOptional.isEmpty()) {
                if (io.quarkus.runtime.LaunchMode.current() == io.quarkus.runtime.LaunchMode.DEVELOPMENT) {
                    String fallbackEmail = email != null ? email : "dev-user@example.com";
                    Optional<User> existingUserByEmail = userRepository.findByEmail(fallbackEmail);
                    
                    if (existingUserByEmail.isPresent()) {
                        System.out.println("INFO: Usuario dev encontrado por email. Usando usuario existente: " + fallbackEmail);
                        user = existingUserByEmail.get();
                    } else {
                        System.out.println("INFO: Usuario dev no encontrado en BD. Auto-registrando: " + fallbackEmail);
                        User newUser = new User();
                        newUser.setId(java.util.UUID.randomUUID());
                        newUser.setActive(true);
                        newUser.setFirebaseUuid(firebaseUuid);
                        newUser.setEmail(fallbackEmail);
                        newUser.setFullName(fullName != null ? fullName : "Dev User");
                        newUser.setRole("USER");
                        user = userRepository.create(newUser);
                    }
                } else {
                    requestContext.abortWith(
                            Response.status(401).build()
                    );
                    return;
                }
            } else {
                user = userOptional.get();
            }

            CurrentUser currentUser = new CurrentUser(
                    user.getId(), user.getFirebaseUuid(), user.getEmail(), user.getRole(), user.getFullName()
            );
            authenticatedUserContext.setCurrentUser(currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            requestContext.abortWith(
                    Response.status(401).build()
            );
        }
    }

    private String getJsonField(String json, String fieldName) {
        String pattern = "\"" + fieldName + "\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        pattern = "\"" + fieldName + "\"\\s*:\\s*([^,\\}]+)";
        matcher = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return matcher.group(1).trim().replace("\"", "");
        }
        return null;
    }
}
