package com.example.springbootwebfluxsecuritydao.service;

import com.example.springbootwebfluxsecuritydao.entity.User;

public interface UserService extends CrudService<User> {
    User findByUsername(String username);
}
