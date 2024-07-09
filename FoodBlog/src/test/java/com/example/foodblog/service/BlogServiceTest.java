package com.example.foodblog.service;

import com.example.foodblog.model.Blog;
import com.example.foodblog.model.User;
import com.example.foodblog.respository.IBlogRepository;
import com.example.foodblog.respository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BlogServiceTest {

    @Autowired
    private BlogService blogService;

    @MockBean
    private IBlogRepository blogRepository;

    @MockBean
    private IUserRepository userRepository;

    // ========== ADD BLOG =========
    @Test
    public void testAddBlog() throws Exception {
        User user = new User();
        user.setId(1); // Sample user ID for testing
        Blog blog = new Blog(1, "Test Blog", "Test Content", 0, new Date(), null, null, user, null);

        // Mocking userRepository to return a user. Needed because user must exist before blog
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        // Mocking blogRepository to return the saved blog
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        // Adding the blog
        Blog savedBlog = blogService.addBlog(blog);

        // Verifying that userRepository.findById(1) is called once
        verify(userRepository, times(1)).findById(1);
        // Verifying that blogRepository.save(blog) is called once
        verify(blogRepository, times(1)).save(blog);

        // Asserting that the saved blog matches the returned blog
        assertEquals(blog, savedBlog);
    }

    // ========== GET ALL BLOGS =========
    @Test
    public void testGetAllBlogs() {
        // Creating a list of mock blogs
        List<Blog> blogs = Arrays.asList(new Blog(), new Blog(), new Blog());

        // Mocking blogRepository to return the list of blogs
        when(blogRepository.findAll()).thenReturn(blogs);

        // Getting all blogs
        List<Blog> allBlogs = blogService.getAllBlogs();

        // Verifying that blogRepository.findAll() is called once
        verify(blogRepository, times(1)).findAll();

        // Asserting that the size of allBlogs is 3
        assertEquals(3, allBlogs.size());
    }

    // ========== GET BLOG BY ID =========
    @Test
    public void testGetBlogById() throws Exception {
        Blog blog = new Blog(1, "Test Blog", "Test Content", 0, new Date(), null, null, new User(), null);

        // Mocking blogRepository to return the blog by ID
        when(blogRepository.findById(1)).thenReturn(Optional.of(blog));

        // Getting blog by ID
        Blog foundBlog = blogService.getBlogById(1);

        // Verifying that blogRepository.findById(1) is called once
        verify(blogRepository, times(1)).findById(1);

        // Asserting that the found blog matches the original blog
        assertEquals(blog, foundBlog);
    }

    // ========== GET BLOG BY TITLE =========
    @Test
    public void testGetBlogByTitle() {
        Blog blog = new Blog(1, "Test Blog", "Test Content", 0, new Date(), null, null, new User(), null);

        // Mocking blogRepository to return the blog by title
        when(blogRepository.findBlogByTitle("Test Blog")).thenReturn(blog);

        // Getting blog by title
        Blog foundBlog = blogService.getBlogByTitle("Test Blog");

        // Verifying that blogRepository.findBlogByTitle("Test Blog") is called once
        verify(blogRepository, times(1)).findBlogByTitle("Test Blog");

        // Asserting that the found blog matches the original blog
        assertEquals(blog, foundBlog);
    }

    // ========== UPDATE BLOG =========
    @Test
    public void testUpdateBlog() throws Exception {
        // Creating a mock user
        User user = new User(1, "username1", "email@email.com", "password", new Date(), null, null, null);
        // adding mock user to the mock database
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Creating a mock blog
        Date creationDate = new Date();
        Blog blogToUpdate = new Blog(1, "Test Blog", "Test Content", 0, creationDate, null, null, user, null);
        // mocking getting the blog from the database
        when(blogRepository.findById(1)).thenReturn(Optional.of(blogToUpdate));

        // creating a blog to update the other blog
        Date updatedDate = new Date(); // Assuming the update happened at this time
        Blog updatedBlog = new Blog(1, "Updated Blog", "Updated Content", 0, creationDate, updatedDate, null, user, null);

        // Mocking blogRepository to return the updated blog
        when(blogRepository.save(any(Blog.class))).thenReturn(updatedBlog);

        // Updating the blog
        Blog result = blogService.updateBlog(1, updatedBlog);

        // Verifying that blogRepository.save(updatedBlog) is called once
        // validates what the code does - not as important
        verify(blogRepository, times(1)).findById(1);
        verify(blogRepository, times(1)).save(any(Blog.class));

        // Asserting that the updated blog matches the result
        // validates the output of the code - most important
        assertEquals(updatedBlog.getTitle(), result.getTitle());
        assertEquals(updatedBlog.getContent(), result.getContent());

        // Asserting that the lastModified field is set as expected
        //assertEquals(updatedDate, result.getLastModified());
    }

    // ========== DELETE BLOG =========
    @Test
    public void testDeleteBlog() throws Exception {
        Blog blog = new Blog(1, "Test Blog", "Test Content", 0, new Date(), null, null, new User(), null);

        // Mocking blogRepository to return the blog to delete
        when(blogRepository.findById(1)).thenReturn(Optional.of(blog));

        // Deleting the blog
        blogService.deleteBlog(1);

        // Verifying that blogRepository.delete(blog) is called once
        verify(blogRepository, times(1)).delete(blog);
    }
}
