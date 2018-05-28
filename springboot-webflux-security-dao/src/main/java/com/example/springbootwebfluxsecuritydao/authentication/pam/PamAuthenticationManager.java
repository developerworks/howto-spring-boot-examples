package com.example.springbootwebfluxsecuritydao.authentication.pam;

import org.jvnet.libpam.PAM;
import org.jvnet.libpam.PAMException;
import org.jvnet.libpam.UnixUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * https://github.com/franktylerva/api-gateway/blob/master/src/main/java/gov/dea/apigateway/security/pam/PAMAuthenticationManager.java
 */
public class PamAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = null;
        // 用户名
        String username = authentication.getName();
        // 密码
        String credentials = (String) authentication.getCredentials();
        try {
            PAM pam = new PAM("sshd");
            UnixUser unixUser = pam.authenticate(username, credentials);
            // 把组名作为职权
            List<GrantedAuthority> grantedAuthorities = unixUser.getGroups().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            // 实例化 UserDetails
            User userDetails = new User(username, "", grantedAuthorities);
            // 创建用户名密码认证令牌
            token = new UsernamePasswordAuthenticationToken(username, credentials, grantedAuthorities);
            token.setDetails(userDetails);
        } catch (PAMException e) {
            e.printStackTrace();
            throw new BadCredentialsException( e.getMessage() );
        }

        return token;
    }
}
