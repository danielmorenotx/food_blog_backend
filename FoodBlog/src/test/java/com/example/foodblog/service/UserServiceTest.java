package com.example.foodblog.service;

import com.example.foodblog.model.User;
import com.example.foodblog.respository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private IUserRepository userRepository;

    Date userCreationDate = new Date();
    User testUser = new User(1, "username1", "email@email.com", "password", userCreationDate, null, null, null);

    // ======== ADD USER ========
    @Test
    public void testAddUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.addUser(testUser);

        verify(userRepository, times(1)).save(testUser);

        assertEquals(testUser, savedUser);
    }

    // ======== GET ALL USERS ========
    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();

        verify(userRepository, times(1)).findAll();

        assertEquals(allUsers, users);
        assertEquals(3, allUsers.size());
    }

    // ======== GET USER BY ID ========
    @Test
    public void testGetUserById() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserById(1);

        verify(userRepository, times(1)).findById(1);

        assertEquals(testUser, foundUser);
    }

    // ======== GET USER BY USERNAME ========
    @Test
    public void testGetUserByUsername() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(testUser);

        User foundUser = userService.getUserByUsername(testUser.getUsername());

        verify(userRepository, times(1)).findUserByUsername(testUser.getUsername());

        assertEquals(testUser, foundUser);
    }

    // ======== UPDATE USER ========
    @Test
    public void testUpdateUser() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        User updatedUser = testUser;
        updatedUser.setUsername("updatedUsername");
        updatedUser.setEmail("example@email.com");

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1, updatedUser);

        verify(userRepository, times(1)).save(updatedUser);

        assertEquals(updatedUser, result);
    }

    // ======== DELETE USER ========
    @Test
    public void testDeleteUser() throws Exception {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        userService.deleteUser(1);

        verify(userRepository, times(1)).delete(testUser);
    }
}
