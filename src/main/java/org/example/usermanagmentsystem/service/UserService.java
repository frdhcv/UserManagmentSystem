package org.example.usermanagmentsystem.service;

import org.example.usermanagmentsystem.entities.User;
import org.example.usermanagmentsystem.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface UserService {
    User createUser(String username, int age, LocalDate createdAt, LocalDate updatedAt) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    Optional<User> getUserById(int id) throws UserNotFoundException;

    void deleteUserById(Long id) throws UserNotFoundException;

}



