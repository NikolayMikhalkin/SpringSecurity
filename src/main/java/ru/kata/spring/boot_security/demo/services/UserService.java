package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.Collection;

public interface UserService {

    Collection<User> findAll();

    User getById(Integer id);

    User findByUsername(String username);

    void save(User user);

    void getDelete(Integer userId);

    void update(User user);
}
