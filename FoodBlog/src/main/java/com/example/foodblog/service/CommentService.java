package com.example.foodblog.service;

import com.example.foodblog.dto.CommentDTO;
import com.example.foodblog.dto.NotificationDTO;
import com.example.foodblog.model.Blog;
import com.example.foodblog.model.Comment;
import com.example.foodblog.model.User;
import com.example.foodblog.respository.IBlogRepository;
import com.example.foodblog.respository.ICommentRepository;
import com.example.foodblog.respository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    ICommentRepository commentRepository;
    @Autowired
    IBlogRepository blogRepository;
    @Autowired
    IUserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    // ========== CRUD OPERATIONS ==============
    // ----- add comment -------
    public Comment addComment(CommentDTO comment) throws Exception {
        User user = userRepository.findById(comment.getUserId())
                .orElseThrow(() -> new Exception("User not found."));

        Blog blog = blogRepository.findById(comment.getBlogId())
                .orElseThrow(() -> new Exception("Blog not found."));

        Comment newComment = Comment.builder()
                .text(comment.getText()) // sets the text of the comment to the text given to the method
                .commenterUsername(user.getUsername())
                .likes(comment.getLikes()) // sets likes
                .blog(blog) // adds the blog on which the comment was made to the comment
                .user(user) // adds the user who made the comment to the comment
                .build();

        commentRepository.save(newComment); // saves the comment to the DB, generates a comment ID

        blog.getComments().add(newComment);
        blogRepository.save(blog);

        // sending notification to the owner of the blog
        sendNotification(user, blog, newComment);

        return newComment;
    }

    // notification method for whenever a comment is posted
    public void sendNotification(User user, Blog blog, Comment comment) {

        NotificationDTO notificationDTO = NotificationDTO.builder() // start to build based on NotificationDTO
                .text(user.getUsername() + " left a comment on your blog '" + "'" + blog.getTitle() + "'.") // sets the text of the notification, pulls username and blog title from the user and blog objects used for the comment
                .recipientId(blog.getUser().getId()) // "owner" is the blog owner, so gets the user ID of the blog object
                .commentId(comment.getId()) // ID of the new comment just created
                .usernameOfCommenter(user.getUsername()) // username of the user that left the comment
                .build();


        restTemplate.postForEntity( // calls the rest template - POST request
                "http://localhost:8081/notifications", // endpoint of the notification microservice to add a new notification
                notificationDTO, // Request body is a NotificationDTO object just build
                NotificationDTO.class // Response type expected from the server - NotificationDTO class object
        );
    }


    // ----- get all comments -------
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    // ----- get comment by ID -------
    public Comment getCommentById(Integer id) throws Exception {
        return commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Comment not found with ID " + id));
    }

    // ----- get comment by commenter -----
    public List<Comment> getCommentsByCommenter(String username) {

        User user = userRepository.findUserByUsername(username);

        return user.getComments();
    }

    // ----- update comment -----
    public Comment updateComment(Integer id, Comment updatedCommentDetails) throws Exception {
        Comment commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Comment not found with ID " + id));

        commentToUpdate.setText(updatedCommentDetails.getText());

        return commentRepository.save(commentToUpdate);
    }

    // ----- delete comment -----
    public void deleteComment(Integer id) throws Exception {
        Comment commentToDelete = commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Comment not found with ID " + id));

        commentRepository.delete(commentToDelete);
    }
}