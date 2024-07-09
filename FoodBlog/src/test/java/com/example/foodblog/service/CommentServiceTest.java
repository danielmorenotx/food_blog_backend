package com.example.foodblog.service;

import com.example.foodblog.dto.CommentDTO;
import com.example.foodblog.model.Blog;
import com.example.foodblog.model.Comment;
import com.example.foodblog.model.User;
import com.example.foodblog.respository.IBlogRepository;
import com.example.foodblog.respository.ICommentRepository;
import com.example.foodblog.respository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @MockBean
    private ICommentRepository commentRepository;
    @MockBean
    private IUserRepository userRepository;
    @MockBean
    private IBlogRepository blogRepository;
    @MockBean
    private RestTemplate restTemplate;

    private User testUser = new User(1, "username1", "email1@example.com", "testpw", new Date(), null, null, null);
    private Blog testBlog = new Blog(1, "Test Title", "Test Content", 0, new Date(), new Date(), new ArrayList<>(), testUser, null); // create blog object to test with

    Date commentDate = new Date();
    private Comment testComment = new Comment(1, "Test Comment", commentDate, "username1", 0, testBlog, testUser);


    @Test
    public void testAddComment() throws Exception {
        // Given
        CommentDTO commentDTO = CommentDTO.builder() // creating a comment DTO to use
                .text("Test comment")
                .timestamp(new Date())
                .likes(0)
                .blogId(1)
                .userId(1)
                .build();

        // user and blogs must exist in order for a comment to be added
        User user = new User(1, "username1", "email@example.com", "password", new Date(), null, null, null);
        Blog blog = new Blog(1, "Test Title", "Test Content", 0, new Date(), new Date(), new ArrayList<>(), user, null);

        // Mock UserRepository behavior - return a user when method called
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Mock BlogRepository behavior - return a blog when method called
        when(blogRepository.findById(1)).thenReturn(Optional.of(blog));

        // Mock CommentRepository behavior
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> { // when saved invoke...
            Comment savedComment = invocation.getArgument(0); //
            savedComment.setId(1); // Assigning a fake ID
            return savedComment;
        });

        // When
        Comment savedComment = commentService.addComment(commentDTO);

        // Then
        assertNotNull(savedComment);
        assertEquals("Test comment", savedComment.getText());
        assertEquals(0, savedComment.getLikes());
        assertEquals("username1", savedComment.getCommenterUsername());
        assertEquals(blog, savedComment.getBlog());
        assertEquals(user, savedComment.getUser());

        // Verify that UserRepository's findById method was called once
        verify(userRepository, times(1)).findById(1);

        // Verify that BlogRepository's findById method was called once
        verify(blogRepository, times(1)).findById(1);

        // Verify that CommentRepository's save method was called once
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    public void testGetAllComments() {
        List<Comment> comments = Arrays.asList(new Comment(), new Comment(), new Comment());

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> allComments = commentService.getAllComments();

        verify(commentRepository, times(1)).findAll();

        assertEquals(3, allComments.size());
    }

    @Test
    public void testGetCommentById() throws Exception {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.ofNullable(testComment));

        Comment foundComment = commentService.getCommentById(1);

        verify(commentRepository, times(1)).findById(1);

        assertEquals(testComment, foundComment);

    }

    @Test
    public void testGetCommentByCommenter() {
        // Mocking userRepository behavior to return a valid User object
        when(userRepository.findUserByUsername(anyString())).thenReturn(testUser);

        // Mocking commentRepository behavior
        List<Comment> comments = Arrays.asList(testComment, testComment);
        testUser.setComments(comments);
        when(commentRepository.findCommentsByCommenterUsername(anyString())).thenReturn(comments);

        // Call the method under test
        List<Comment> commentsByUser = commentService.getCommentsByCommenter(testComment.getCommenterUsername());

        // Verify that userRepository method was called with the correct argument
        verify(userRepository, times(1)).findUserByUsername(testComment.getCommenterUsername());

        // Assert the result
        assertNotNull(commentsByUser); // Ensure commentsByUser is not null
        assertEquals(2, commentsByUser.size()); // Assuming you expect two comments for the testUser
    }

    @Test
    public void testUpdateComment() throws Exception {// Create updated comment
        Date currentDate = new Date();
        Comment updatedComment = new Comment(1, "Comment text updated", currentDate, "username1", 0, testBlog, testUser);

        when(commentRepository.findById(1)).thenReturn(Optional.of(testComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        Comment result = commentService.updateComment(1, updatedComment);

        // verify(commentRepository, times(1)).save(updatedComment);

        assertEquals(updatedComment, result);
    }

    @Test
    public void testDeleteComment() throws Exception {
        when(commentRepository.findById(1)).thenReturn(Optional.of(testComment));

        commentService.deleteComment(1);

        verify(commentRepository, times(1)).delete(testComment);
    }
}
