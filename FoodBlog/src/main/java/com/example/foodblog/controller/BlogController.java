package com.example.foodblog.controller;

import com.example.foodblog.model.Blog;
import com.example.foodblog.model.Tag;
import com.example.foodblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs") // sets the first point to the API endpoint
@CrossOrigin(origins = "*") // allows requests from anywhere
public class BlogController {

    @Autowired
    BlogService blogService;

    // ========== CRUD OPERATIONS ==============
    // ----- add blog -------
    @PostMapping
    public Blog addBlog(@RequestBody Blog blog) throws Exception {
        return blogService.addBlog(blog);
    }

    // ----- get all blogs -------
    @GetMapping("/all-blogs")
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    // ----- get blog by ID -----
    @GetMapping("/{id}")
    public Blog getBlogById(@PathVariable Integer id) throws Exception {
        return blogService.getBlogById(id);
    }

    // ----- get blog by title -----
    @GetMapping
    public Blog getBlogByTitle(@RequestParam String title) {
        return blogService.getBlogByTitle(title);
    }

    // ----- update blog -----
    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Integer id, @RequestBody Blog updatedBlogDetails) throws Exception {
        return blogService.updateBlog(id, updatedBlogDetails);
    }

    // ----- delete blog -----
    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Integer id) throws Exception {
        blogService.deleteBlog(id);
    }

    // ----- add tag ------
    @PutMapping("/{blogId}/tags/add")
    public Blog addTagToBlog(@PathVariable Integer blogId, @RequestParam String tagName) throws Exception {
        return blogService.addTagToBlog(blogId, tagName);
    }

    // ----- remove tag from blog ------
    @PutMapping("/{blogId}/tags/remove")
    public Blog removeTagFromBlog(@PathVariable Integer blogId, @RequestParam String tagName) throws Exception {
        return blogService.removeTagFromBlog(blogId, tagName);
    }

    // ----- get blogs by tag name ------
    @GetMapping("/tags/{tagName}")
    public List<Blog> getBlogsByTagName(@PathVariable String tagName) {
        return blogService.getBlogsByTagName(tagName);
    }
}