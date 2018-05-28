package com.example.springbootwebfluxsecuritydao.service.impl;

import com.example.springbootwebfluxsecuritydao.entity.Role;
import com.example.springbootwebfluxsecuritydao.exception.ResourceNotFoundException;
import com.example.springbootwebfluxsecuritydao.repository.RoleRepository;
import com.example.springbootwebfluxsecuritydao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<?> listAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public Role getById(Long id) {
        return roleRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException(String.format("角色 %s 不存在", id))
            );
    }

    @Override
    public Role saveOrUpdate(Role domainObject) {
        return roleRepository.save(domainObject);

    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
