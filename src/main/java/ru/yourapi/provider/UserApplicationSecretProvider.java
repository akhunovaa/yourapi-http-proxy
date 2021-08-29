package ru.yourapi.provider;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.yourapi.exception.InvalidUserApplicationSecretException;
import ru.yourapi.exception.UserApplicationNotFoundException;
import ru.yourapi.service.CustomUserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserApplicationSecretProvider {

    private static final String USER_APPLICATION_SECRET_KEY_HEADER_NAME = "X-YourAPI-Key";

    @Autowired
    private StandardPBEStringEncryptor stringEncrypt;

    private static final Logger logger = LoggerFactory.getLogger(UserApplicationSecretProvider.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public Authentication getAuthentication(String secret) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(secret);
        Collection<? extends GrantedAuthority> authorities = setUserAuthorities();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    @SuppressWarnings("deprecation")
    private Collection<GrantedAuthority> setUserAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authority = "user";
        grantedAuthorities.add(new GrantedAuthorityImpl(authority));
        return grantedAuthorities;
    }

    public String resolveSecret(HttpServletRequest req) throws UserApplicationNotFoundException {
        String userApplicationSecret = req.getHeader(USER_APPLICATION_SECRET_KEY_HEADER_NAME);
        if (userApplicationSecret != null) {
            return userApplicationSecret;
        }
        return null;
//        else {
//            throw new UserApplicationNotFoundException();
//        }
    }

    public boolean validateUserApplicationSecret(String secretText) throws InvalidUserApplicationSecretException {
        try {
            stringEncrypt.decrypt(secretText);
            return true;
        } catch (EncryptionOperationNotPossibleException ex) {
            logger.error("Invalid X-YourAPI-key");
            throw new InvalidUserApplicationSecretException();
        }
    }
}