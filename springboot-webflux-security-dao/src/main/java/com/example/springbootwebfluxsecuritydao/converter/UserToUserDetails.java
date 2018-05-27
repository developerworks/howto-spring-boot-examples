package com.example.springbootwebfluxsecuritydao.converter;

import com.example.springbootwebfluxsecuritydao.entity.User;
import com.example.springbootwebfluxsecuritydao.service.impl.UserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserToUserDetails implements Converter<User, UserDetails> {
    @Override
    public UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getEncryptedPassword());
        userDetails.setEnabled(user.getEnabled());
        // 把用户具有的角色添加到职权列表中.
        // 这里角色名称和职权名称相同.
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        userDetails.setAuthorities(authorities);
        return userDetails;
    }
}