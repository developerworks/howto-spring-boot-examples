package com.example.springbootwebfluxsecuritydao.config;

import com.example.springbootwebfluxsecuritydao.entity.Product;
import com.example.springbootwebfluxsecuritydao.entity.Role;
import com.example.springbootwebfluxsecuritydao.entity.User;
import com.example.springbootwebfluxsecuritydao.repository.ProductRepository;
import com.example.springbootwebfluxsecuritydao.service.RoleService;
import com.example.springbootwebfluxsecuritydao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

//@Component
@Slf4j
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private ProductRepository productRepository;
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
    }

    private void loadProducts() {
        Product bakePaper = new Product();
        bakePaper.setDescription("烤乐仕油纸烘焙纸");
        bakePaper.setPrice(new BigDecimal("43.60"));
        bakePaper.setImageUrl("https://img.alicdn.com/imgextra/i3/2088166626/TB20jNuX77myKJjSZFzXXXgDpXa_!!2088166626.jpg_430x430q90.jpg");
        bakePaper.setProductId("520689146676");
        productRepository.save(bakePaper);
        log.info("保存: 烤乐仕油纸烘焙纸 - id: {}", bakePaper.getId());

        Product chocolate = new Product();
        chocolate.setDescription("Lidl历德榛子巧克力酱400g*2罐德国进口榛果可可酱");
        chocolate.setPrice(new BigDecimal("39.00"));
        chocolate.setImageUrl("https://img.alicdn.com/imgextra/i3/3205939162/TB2K5hMelUSMeJjy1zjXXc0dXXa_!!3205939162.jpg_430x430q90.jpg");
        chocolate.setProductId("558173137626");
        productRepository.save(chocolate);
        log.info("保存: Lidl历德榛子巧克力酱 - id: {}", chocolate.getId());
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUsername("user");
        user1.setPassword("user");
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("admin");
        userService.saveOrUpdate(user2);


        User test = new User();
        test.setUsername("test");
        test.setPassword("test");
        userService.saveOrUpdate(test);

    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdate(role);
        log.info("Saved role" + role.getRole());
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdate(adminRole);
        log.info("Saved role" + adminRole.getRole());
    }

    @SuppressWarnings("unchecked")
    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        log.info("=================================== Roles {}", roles);
        List<User> users = (List<User>) userService.listAll();
        log.info("=================================== Users {}", users);

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("user")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("admin")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
}
