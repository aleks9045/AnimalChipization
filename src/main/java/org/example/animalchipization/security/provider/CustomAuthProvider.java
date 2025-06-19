package org.example.animalchipization.security.provider;

import lombok.RequiredArgsConstructor;
import org.example.animalchipization.entity.Account;
import org.example.animalchipization.service.account.impl.AccountValidatorImpl;
import org.example.animalchipization.util.UserAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Authenticates user
 *
 * <p>Decodes Base64, checks it equality with base64 string from database<br>
 * Throws BadCredentialsException if authentication failed
 *
 * @author Aleksey
 */
@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {

    private final AccountValidatorImpl accountValidator;


    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getName();
        String password = auth.getCredentials().toString();

        String incomingBase64 = UserAuthentication.encodeEmailAndPassword(email, password);

        Account account = accountValidator.getByEmail(email);

        if (incomingBase64.equals(account.getBase64())) {
            return new UsernamePasswordAuthenticationToken(email, password,
                    List.of(new SimpleGrantedAuthority("USER")));
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public static String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
