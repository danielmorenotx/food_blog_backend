package com.example.foodblog.controller;

import com.example.foodblog.model.User;
import com.example.foodblog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User testUser = new User(1, "testuser", "test@example.com", "password", new Date(), null, null, null);

    // ====== ADD USER ======
    // happy
    @Test
    public void testAddUser() throws Exception {
        when(userService.addUser(any(User.class))).thenReturn(testUser);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());
    }

    // sad - missing info
    @Test
    public void testAddUserMissingInfo() throws Exception {
        User incompleteUser = new User(1, null, "test@example.com", "password", new Date(), null, null, null);
        when(userService.addUser(any(User.class))).thenReturn(incompleteUser);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incompleteUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User(1, "testuser1", "test1@example.com", "password1", new Date(), null, null, null));
        testUsers.add(new User(2, "testuser2", "test2@example.com", "password2", new Date(), null, null, null));
        when(userService.getAllUsers()).thenReturn(testUsers);
        mockMvc.perform(get("/users/all-users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
        User testUser = new User(1, "testuser", "test@example.com", "password", new Date(), null, null, null);
        when(userService.getUserById(1)).thenReturn(testUser);
        mockMvc.perform(get("/users/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindUserByUsername() throws Exception {
        when(userService.getUserByUsername("testuser")).thenReturn(testUser);
        mockMvc.perform(get("/users?username=testuser"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(1, testUser)).thenReturn(testUser);
        mockMvc.perform(put("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(1);
    }
}