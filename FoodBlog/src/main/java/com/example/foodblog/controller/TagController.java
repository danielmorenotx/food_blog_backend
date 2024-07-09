package com.example.foodblog.controller;

import com.example.foodblog.model.Tag;
import com.example.foodblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    // ========== CRUD OPERATIONS ==============
    // ----- add tag -------
    @PostMapping
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    // ----- get all tags -------
    @GetMapping("/all-tags")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    // ----- get tag by ID -------
    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Integer id) throws Exception {
        return tagService.getTagById(id);
    }

    // ----- get tag by name -----
    @GetMapping("/by-name")
    public Tag getTagByName(@RequestParam String name) {
        return tagService.getTagByName(name);
    }

    // ----- update tag -----
    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable Integer id, @RequestBody Tag updatedTagDetails) throws Exception {
        return tagService.updateTag(id, updatedTagDetails);
    }

    // ----- delete tag -----
    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Integer id) throws Exception {
        tagService.deleteTag(id);
    }
}
