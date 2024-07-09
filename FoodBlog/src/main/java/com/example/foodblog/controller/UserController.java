package com.example.foodblog.controller;

import com.example.foodblog.dto.NotificationDTO;
import com.example.foodblog.model.User;
import com.example.foodblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // sets the first point to the API endpoint
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    // ========== CRUD OPERATIONS ==============
    // ----- add user -------
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // ----- add users -------
    @PostMapping("add-users")
    public List<User> addUsers(@RequestBody List<User> users) {
        return userService.addUsers(users);
    }

    // ----- get all users -------
    @GetMapping("all-users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ----- get user by ID -----
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) throws Exception {
        return userService.getUserById(id);
    }

    // ----- get user by username -----
    @GetMapping
    public User getUserByUsername(@RequestParam String username) throws Exception {
        return userService.getUserByUsername(username);
    }

    // ----- update user -----
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User updatedUserDetails) throws Exception {
        return userService.updateUser(id, updatedUserDetails);
    }

    // ----- delete user -----
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) throws Exception {
        userService.deleteUser(id);
    }
    
    // ----- get all notifications sent to a user ------
    @GetMapping("/notifications/{id}")
    public List<NotificationDTO> getUserNotifications(@PathVariable Integer id) {
        return userService.getUserNotifications(id);
    }
}
