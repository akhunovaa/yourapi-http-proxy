package ru.yourapi.service;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.yourapi.dto.UserPrincipal;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StandardPBEStringEncryptor stringEncrypt;

    @Override
    public UserDetails loadUserByUsername(String secretKey) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = new UserPrincipal();
        String[] decryptedText = stringEncrypt.decrypt(secretKey).split("_");
        Long id = Long.valueOf(decryptedText[0]);
        String login = decryptedText[1];
        userPrincipal.setId(id);
        userPrincipal.setLogin(login);
        return userPrincipal;
    }
}