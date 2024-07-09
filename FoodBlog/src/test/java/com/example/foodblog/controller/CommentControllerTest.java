package com.example.foodblog.controller;

import com.example.foodblog.dto.CommentDTO;
import com.example.foodblog.model.Comment;
import com.example.foodblog.service.CommentService;
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

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // Mocking CommentService bean
    private CommentService commentService;

    private Comment testComment = new Comment(1, "Test Comment", new Date(), "user1", 0, null, null);

    private List<Comment> testComments = new ArrayList<>();

    // ======= ADD COMMENT ======
    // happy path
    @Test
    public void testAddComment() throws Exception {
        // Stubbing the behavior of CommentService
        when(commentService.addComment(any(CommentDTO.class))).thenReturn(testComment);

        // Performing POST request and expecting a successful response
        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk());
    }

    // sad path - comment has no body
    @Test
    public void testAddCommentMissingBody() throws Exception {
        // Create a CommentDTO with a missing body
        CommentDTO incompleteCommentDTO = new CommentDTO("", new Date(), 0, 1, 1);

        // Perform POST request with the incomplete CommentDTO
        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incompleteCommentDTO)))
                .andExpect(status().isBadRequest()); // Expect a 400 Bad Request response
    }



    // Test for getting all comments
    @Test
    public void testGetAllComments() throws Exception {
        // Add comments to list of comments
        testComments.add(testComment);
        testComments.add(new Comment());

        // Stubbing the behavior of CommentService
        when(commentService.getAllComments()).thenReturn(testComments);

        // Performing GET request for all comments and expecting a successful response
        mockMvc.perform(get("/comments/all-comments"))
                .andExpect(status().isOk());
    }

    // Test for getting a comment by ID
    @Test
    public void testGetCommentById() throws Exception {
        // Stubbing the behavior of CommentService
        when(commentService.getCommentById(1)).thenReturn(testComment);

        // Performing GET request for a comment by ID and expecting a successful response
        mockMvc.perform(get("/comments/{id}", 1))
                .andExpect(status().isOk());
    }

    // Test for getting comments by commenter
    @Test
    public void testGetCommentsByCommenter() throws Exception {
        testComments.add(testComment);
        testComments.add(new Comment());

        // Stubbing the behavior of CommentService
        when(commentService.getCommentsByCommenter("commenter")).thenReturn(testComments);

        // Performing GET request for comments by commenter and expecting a successful response
        mockMvc.perform(get("/comments/by-commenter").param("username", "commenter"))
                .andExpect(status().isOk());
    }

    // Test for updating a comment
    @Test
    public void testUpdateComment() throws Exception {

        // Stubbing the behavior of CommentService
        when(commentService.updateComment(1, testComment)).thenReturn(testComment);

        // Performing PUT request to update a comment and expecting a successful response
        mockMvc.perform(put("/comments/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testComment)))
                .andExpect(status().isOk());
    }

    // Test for deleting a comment
    @Test
    public void testDeleteComment() throws Exception {
        // Performing DELETE request to delete a comment and expecting a successful response
        mockMvc.perform(delete("/comments/{id}", 1))
                .andExpect(status().isOk());

        // Verifying that the deleteComment method of CommentService is called once with the correct ID
        verify(commentService, times(1)).deleteComment(1);
    }
}

