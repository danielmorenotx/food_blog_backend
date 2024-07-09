package com.example.foodblog.respository;

import com.example.foodblog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepository extends JpaRepository<Blog, Integer> {

    Blog findBlogByTitle(String title);
}
