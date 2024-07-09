package com.example.foodblog.service;

import com.example.foodblog.dto.NotificationDTO;
import com.example.foodblog.model.User;
import com.example.foodblog.respository.IBlogRepository;
import com.example.foodblog.respository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IBlogRepository blogRepository;
    @Autowired
    private RestTemplate restTemplate;

    // ========== CRUD OPERATIONS ==============
    // ----- add user -------
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // ----- add users -------
    public List<User> addUsers(List<User> users) {
        List<User> savedUsers = new ArrayList<>(); // creates a list to store the users in
        for (User user : users) { // for every user in  the list of provided users
            savedUsers.add(userRepository.save(user)); // add the saved user to the list of saved users
        }
        return savedUsers; // return the list of saved users
    }

    // ----- get all users -------
    public List<User> getAllUsers() {
        return userRepository.findAll(); // returns a list of blog objects
    }

    // ----- get user by ID -----
    public User getUserById(Integer id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with ID " + id));
    }

    // ----- get user by username -----
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    // ----- update user -----
    public User updateUser(Integer id, User updatedUserDetails) throws Exception {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Blog not found with ID " + id));

        userToUpdate.setUsername(updatedUserDetails.getUsername());
        userToUpdate.setEmail(updatedUserDetails.getEmail());
        userToUpdate.setPassword(updatedUserDetails.getPassword());

        return userRepository.save(userToUpdate); // saves the updated blog to the DB
    }

    // ----- delete user -----
    public void deleteUser(Integer id) throws Exception {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found with ID " + id));

        userRepository.delete(userToDelete);
    }

    public List<NotificationDTO> getUserNotifications(Integer id) {

        ResponseEntity<NotificationDTO[]> responseEntity = restTemplate.getForEntity(
                "http://localhost:8081/notifications/users/{userId}", // endpoint to find by userId
                NotificationDTO[].class,
                id // passes the userId into the endpoint
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            NotificationDTO[] notificationDTOS = responseEntity.getBody();

            if (notificationDTOS != null) {
                return Arrays.asList(notificationDTOS);
            }
        }
        return null;
    }
}