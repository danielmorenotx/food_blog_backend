package com.example.foodblog.controller;

import com.example.foodblog.dto.CommentDTO;
import com.example.foodblog.model.Comment;
import com.example.foodblog.model.Tag;
import com.example.foodblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    // ========== CRUD OPERATIONS ==============
    // ----- add comment -------
    @PostMapping
    public Comment addComment(@RequestBody CommentDTO comment) throws Exception { // instead of a comment object I receive a comment DTO
        return commentService.addComment(comment);
    }

    // ----- get all comments -------
    @GetMapping("/all-comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    // ----- get comment by ID -------
    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Integer id) throws Exception {
        return commentService.getCommentById(id);
    }

    // ----- get blog by commenter -----
    @GetMapping("/by-commenter")
    public List<Comment> getCommentsByCommenter(@RequestParam String username) {
        return commentService.getCommentsByCommenter(username);
    }

    // ----- update comment -----
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Integer id, @RequestBody Comment updatedCommentDetails) throws Exception {
        return commentService.updateComment(id, updatedCommentDetails);
    }

    // ----- delete comment -----
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) throws Exception {
        commentService.deleteComment(id);
    }
}