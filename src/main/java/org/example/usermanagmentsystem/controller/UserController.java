package org.example.usermanagmentsystem.controller;

import org.example.usermanagmentsystem.entities.User;
import org.example.usermanagmentsystem.exception.UserNotFoundException;
import org.example.usermanagmentsystem.service.UserService;
import org.example.usermanagmentsystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/createUser")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) throws SQLException {
        User createuser = userService.createUser(user.getUserName(), user.getAge(),user.getCreatedAt(),user.getUpdatedAt());
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        return ResponseEntity.ok(map);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() throws SQLException, UserNotFoundException {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/delete/id")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws SQLException, UserNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}


