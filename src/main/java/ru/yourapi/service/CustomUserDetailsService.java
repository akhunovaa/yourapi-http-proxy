package ru.yourapi.service;

import ru.yourapi.dto.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${auth.tokenSecret}")
    private String secretKey;

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = new UserPrincipal();
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Long id = Long.valueOf(claims.getId());
        String login = (String) claims.get("login");
        String email = (String) claims.get("email");
        String issuer = claims.getIssuer();
        Collection<? extends GrantedAuthority> authorities;
        authorities = (List<GrantedAuthority>) claims.get("authorities");
        Date issuedDate = claims.getIssuedAt();
        Date expireDate = claims.getExpiration();
        if (new Date().after(expireDate)) {
            userPrincipal.setExpired(true);
        }
        userPrincipal.setId(id);
        userPrincipal.setLogin(login);
        userPrincipal.setEmail(email);
        userPrincipal.setIssuer(issuer);
        userPrincipal.setAuthorities(authorities);
        return userPrincipal;
    }
}