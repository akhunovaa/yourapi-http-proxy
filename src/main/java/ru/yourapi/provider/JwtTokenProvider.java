package ru.yourapi.provider;

import ru.yourapi.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${auth.tokenSecret}")
    private String secretKey;

    @Value("${auth.expire.time}")
    private String validityInMilliseconds;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(token);
        Collection<? extends GrantedAuthority> authorities = setUserAuthorities((List<GrantedAuthority>) userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return Long.valueOf(claims.getId());
    }

    @SuppressWarnings("deprecation")
    public Collection<GrantedAuthority> setUserAuthorities(List<GrantedAuthority> auths) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authority = (String) ((LinkedHashMap) auths.get(0)).get("authority");
        grantedAuthorities.add(new GrantedAuthorityImpl(authority));
        return grantedAuthorities;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}