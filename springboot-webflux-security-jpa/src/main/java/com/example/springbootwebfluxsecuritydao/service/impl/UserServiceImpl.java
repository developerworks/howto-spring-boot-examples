package com.example.springbootwebfluxsecuritydao.service.impl;

import com.example.springbootwebfluxsecuritydao.entity.User;
import com.example.springbootwebfluxsecuritydao.exception.ResourceNotFoundException;
import com.example.springbootwebfluxsecuritydao.repository.UserRepository;
import com.example.springbootwebfluxsecuritydao.service.EncryptionService;
import com.example.springbootwebfluxsecuritydao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EncryptionService encryptionService;

    @Override
    public User findByUsername(String username) {
        return userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("用户 % 不存在", username))
            );
    }

    @Override
    public List<?> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(Long id) {
        return userRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("用户 %s 不存在", id))
            );
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        if (domainObject.getPassword() != null) {
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }
        return userRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
