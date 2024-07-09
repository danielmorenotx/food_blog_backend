package com.example.foodblog.controller;

import com.example.foodblog.model.Address;
import com.example.foodblog.model.Blog;
import com.example.foodblog.model.User;
import com.example.foodblog.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    private Address testAddress = new Address(1, "987 Fake Street", "Austin", "TX", "78795", "US");
    private User testUser = new User(1, "username1", "email1@example.com", "testpw", new Date(), null, testAddress, null);
    private Blog testBlog = new Blog(1, "Test Title", "Test Content", 0, new Date(), new Date(), null, testUser, null); // create blog object to test with

    // ======== ADD BLOG =========
    // ----- happy path -----
    @Test
    public void testAddBlog() throws Exception {
        mockMvc.perform(post("/blogs") // send to endpoint
                .contentType(MediaType.APPLICATION_JSON) // JSON type
                .content(new ObjectMapper().writeValueAsString(testBlog))) // turns json to string
                .andExpect(status().isOk()); // expects 200 code returned
    }

    // ----- sad paths -----
    // missing user - blogs must be posted by people who are registered on the site
    @Test
    public void testAddBlogMissingUser() throws Exception {
        Blog blogMissingInfo = new Blog(1, "Test Title", "Test Content", 0, new Date(), new Date(), null, null, null);

        // Assuming the absence of the user is not allowed
        // Expect a 400 Bad Request response
        mockMvc.perform(post("/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(blogMissingInfo)))
                .andExpect(status().isBadRequest());
    }

    // ======== GET ALL BLOGS =========
    // ----- happy path -----
    @Test
    public void testGetAllBlogs() throws Exception {
        mockMvc.perform(get("/blogs/all-blogs"))
                .andExpect(status().isOk());

        verify(blogService, times(1)).getAllBlogs();
    }

    // ======== GET BLOG BY ID =========
    // ----- happy path -----
    @Test
    public void testGetBlogById() throws Exception {
        when(blogService.getBlogById(anyInt())).thenReturn(testBlog);

        mockMvc.perform(get("/blogs/{id}", 1))
                .andExpect(status().isOk());

        verify(blogService, times(1)).getBlogById(1);
    }

    // ======== GET BLOG BY TITLE =========
    // ----- happy path -----
    @Test
    public void testGetBlogByTitle() throws Exception {
        when(blogService.getBlogByTitle(anyString())).thenReturn(testBlog);

        mockMvc.perform(get("/blogs?title={title}", "Test Title"))
                .andExpect(status().isOk());

        verify(blogService, times(1)).getBlogByTitle("Test Title");
    }

    // ======== UPDATE BLOG =========
    // ----- happy path -----
    @Test
    public void testUpdateBlog() throws Exception {
        Blog mockBlogUpdates = new Blog(1, "Real title", "Test Content updated", 0, null, null, null, testUser, null);

        // Convert object into JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String blogJson = objectMapper.writeValueAsString(mockBlogUpdates);

        when(blogService.addBlog(mockBlogUpdates)).thenReturn(mockBlogUpdates);

        mockMvc.perform(put("/blogs/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(blogJson))
                .andExpect(status().isOk());
                //.andExpect(content().string(blogJson)); // Response should be the same JSON as request

        verify(blogService, times(1)).updateBlog(1, mockBlogUpdates);
    }

    // ======== DELETE BLOG =========
    // ----- happy path -----
    @Test
    public void testDeleteBlog() throws Exception {
        int blogToDelete = 1; // mock ID to delete

        // mock behavior of method - do nothing when deleteBlog is called on blogToDelete
        doNothing().when(blogService).deleteBlog(blogToDelete);

        // mock DELETE request
        mockMvc.perform(delete("/blogs/{id}", blogToDelete))
                .andExpect(status().isOk());

        // verify that the deleteBlog method was run at least once
        verify(blogService, times(1)).deleteBlog(blogToDelete);
    }

}
