package com.example.foodblog.respository;

import com.example.foodblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByCommenterUsername(String username);
}
