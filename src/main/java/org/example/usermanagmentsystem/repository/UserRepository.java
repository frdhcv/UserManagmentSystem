package org.example.usermanagmentsystem.repository;

import org.example.usermanagmentsystem.entities.User;
import org.example.usermanagmentsystem.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByUsername(String username);

    Long getUserId(String username, String password) throws UserNotFoundException;

    void deleteById(Long id);

    List<User> findAll();
}
