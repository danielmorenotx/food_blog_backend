package com.example.foodblog.service;

import com.example.foodblog.model.Blog;
import com.example.foodblog.model.Tag;
import com.example.foodblog.model.User;
import com.example.foodblog.respository.IBlogRepository;
import com.example.foodblog.respository.ITagRepository;
import com.example.foodblog.respository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    IBlogRepository blogRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ITagRepository tagRepository;

    // ========== CRUD OPERATIONS ==============
    // ----- get all blogs -------
    public Blog addBlog(Blog blog) throws Exception {
        User user = userRepository.findById(blog.getUser().getId())
                .orElseThrow(() -> new Exception("User not found."));
        blog.setUser(user);
        return blogRepository.save(blog);
    }

    // ----- get all blogs -------
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll(); // returns a list of blog objects
    }

    // ----- get blog by ID -----
    public Blog getBlogById(Integer id) throws Exception {
        return blogRepository.findById(id)
                .orElseThrow(() -> new Exception("Blog not found with ID " + id));
    }

    // ----- get blog by title -----
    public Blog getBlogByTitle(String title) {
        return blogRepository.findBlogByTitle(title);
    }

    // ----- update blog -----
    public Blog updateBlog(Integer id, Blog updatedBlogDetails) throws Exception {
        Blog blogToUpdate = blogRepository.findById(id)
                .orElseThrow(() -> new Exception("Blog not found with ID " + id));

        blogToUpdate.setTitle(updatedBlogDetails.getTitle());
        blogToUpdate.setContent(updatedBlogDetails.getContent());
        blogToUpdate.setLastModified(new Date()); // will set the date to whenever this method is run

        // Retrieve the user associated with the updated blog
        User user = userRepository.findById(updatedBlogDetails.getUser().getId())
                .orElseThrow(() -> new Exception("User not found."));

        // Set the retrieved user on the blog
        blogToUpdate.setUser(user);

        return blogRepository.save(blogToUpdate); // saves the updated blog to the DB
    }

    // ----- delete blog -----
    public void deleteBlog(Integer id) throws Exception {
        Blog blogToDelete = blogRepository.findById(id)
                .orElseThrow(() -> new Exception("Blog not found with ID " + id));

        blogRepository.delete(blogToDelete);
    }

    // ------ add tag to blog NOT WORKING YET
    public Blog addTagToBlog(Integer blogId, String tagName) throws Exception {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new Exception("Blog not found with ID " + blogId));

        // find an existing tag
        Tag existingTag = tagRepository.findTagByName(tagName);
        if (existingTag != null) { // if the tag already exists
            blog.getTags().add(existingTag); // add the tag to the blog
            existingTag.getBlogs().add(blog); // add the blog to the tag
        } else {
            Tag newTag = new Tag(); // creating new tag
            newTag.setName(tagName); // setting name of the tag with name provided
            tagRepository.save(newTag); // saving the new tag to the tag DB
            blog.getTags().add(newTag); // adding the new tag to the blog's list of tags
            newTag.getBlogs().add(blog); // add the blog to the new tag
        }

        return blogRepository.save(blog); // saving the blog with the newly added tag
    }

    public Blog removeTagFromBlog(Integer blogId, String tagName) throws Exception {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new Exception("Blog not found with ID " + blogId));

        // find an existing tag
        Tag existingTag = tagRepository.findTagByName(tagName);
        if (existingTag.getName() != null) { // checks if the tag already exists
            blog.getTags().remove(existingTag); // adding the tag to the blog
            // if this is the only blog the tag was on, it will still be in the DB
        } else {
            throw new Exception("This blog does not have a tag '" + tagName + "'");
        }

        return blogRepository.save(blog); // saving the blog with the newly added tag
    }

    // ----- get blogs by tag name ------
    public List<Blog> getBlogsByTagName(String tagName) {
        Tag tag = tagRepository.findTagByName(tagName);

        return tag.getBlogs();
    }
}
