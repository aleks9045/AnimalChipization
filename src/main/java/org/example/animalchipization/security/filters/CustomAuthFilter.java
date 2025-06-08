package org.example.animalchipization.security.filters;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.animalchipization.security.provider.CustomAuthProvider;
import org.example.animalchipization.service.auth.util.Base64Coder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * Filter for spring security
 *
 * <p>Checks authorization header presence and calls CustomAuthProvider to check header's value
 * @author Aleksey
 */
@Component
@NonNullApi
@RequiredArgsConstructor
public class CustomAuthFilter extends OncePerRequestFilter {

    private final CustomAuthProvider authProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        // 1. Проверяем заголовок Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            try {
                // 2. Декодируем Base64
                String base64 = authHeader.substring("Basic ".length());
                String credentials = Base64Coder.decodeBase64(base64);
                String[] parts = credentials.split(":", 2);
                String email = parts[0];
                String password = parts[1];

                // 3. Создаем объект аутентификации
                UsernamePasswordAuthenticationToken authRequest =
                        new UsernamePasswordAuthenticationToken(email, password);

                // 4. Передаем в CustomAuthProvider
                Authentication authResult = authProvider.authenticate(authRequest);

                // 5. Устанавливаем аутентификацию в SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authResult);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        // Allows use any GET request
        else if (request.getMethod().equals("GET")) {
            chain.doFilter(request, response);
            return;
        }
        // Allows to have no authorization header only for registration endpoint
        else if (!request.getRequestURI().startsWith("/registration")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request, response);

    }
}