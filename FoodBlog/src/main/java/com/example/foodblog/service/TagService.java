package com.example.foodblog.service;

import com.example.foodblog.model.Tag;
import com.example.foodblog.respository.IBlogRepository;
import com.example.foodblog.respository.ITagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    ITagRepository tagRepository;
    @Autowired
    IBlogRepository blogRepository;

    // ========== CRUD OPERATIONS ==============
    // ----- add tag -------
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // ----- get all tags -------
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // ----- get tag by ID -------
    public Tag getTagById(Integer id) throws Exception {
        return tagRepository.findById(id)
                .orElseThrow(() -> new Exception("Tag not found with ID " + id));
    }

    // ----- get tag by name -----
    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    // ----- update tag -----
    public Tag updateTag(Integer id, Tag updatedTagDetails) throws Exception {
        Tag tagToUpdate = tagRepository.findById(id)
                .orElseThrow(() -> new Exception("Tag not found with ID " + id));

        tagToUpdate.setName(updatedTagDetails.getName());
        tagToUpdate.setDescription(updatedTagDetails.getDescription());

        return tagRepository.save(tagToUpdate);
    }

    // ----- delete tag -----
    public void deleteTag(Integer id) throws Exception {
        Tag tagToDelete = tagRepository.findById(id)
                .orElseThrow(() -> new Exception("Tag not found with ID " + id));

        tagRepository.delete(tagToDelete);
    }
}

