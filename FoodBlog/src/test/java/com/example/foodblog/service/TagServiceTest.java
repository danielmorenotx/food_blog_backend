package com.example.foodblog.service;

import com.example.foodblog.model.Tag;
import com.example.foodblog.respository.ITagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @MockBean
    private ITagRepository tagRepository;

    private Tag testTag = Tag.builder()
            .id(1)
            .name("tag1")
            .description("tag description")
            .build();
    @Test
    public void testAddTag() {

        // Mock returning the tag when saved
        when(tagRepository.save(any(Tag.class))).thenReturn(testTag);

        // Run the actual service method
        Tag savedTag = tagService.addTag(testTag);

        // Verify that the save method was called once
        verify(tagRepository, times(1)).save(testTag);

        // Assert that the returned tag matches the mock tag
        assertEquals(testTag, savedTag);
    }

    @Test
    public void testAddTagInvalid() {
        // Mock returning the tag when saved
        when(tagRepository.save(any(Tag.class))).thenReturn(testTag);

        // Run the actual service method
        Tag savedTag = tagService.addTag(testTag);

        // Verify that the save method was called once
        verify(tagRepository, times(1)).save(testTag);

        // Assert that the returned tag matches the mock tag
        assertEquals(testTag, savedTag);
    }

    @Test
    public void testGetAllTags() {
        // Create a list of mock tags
        List<Tag> tags = Arrays.asList(new Tag(), new Tag(), new Tag());

        // Mock returning all tags
        when(tagRepository.findAll()).thenReturn(tags);

        // Run the actual service method
        List<Tag> allTags = tagService.getAllTags();

        // Verify that the findAll method was called once
        verify(tagRepository, times(1)).findAll();

        // Assert that the returned list of tags has size 3
        assertEquals(3, allTags.size());
    }

    @Test
    public void testGetTagById() throws Exception {
        // Mock returning the tag when searched by ID
        when(tagRepository.findById(anyInt())).thenReturn(Optional.of(testTag));

        // Run the actual service method
        Tag foundTag = tagService.getTagById(1);

        // Verify that the findById method was called once with ID 1
        verify(tagRepository, times(1)).findById(1);

        // Assert that the returned tag matches the mock tag
        assertEquals(testTag, foundTag);
    }

    @Test
    public void testGetTagByName() {
        // Mock returning the tag when searched by name
        when(tagRepository.findTagByName(anyString())).thenReturn(testTag);

        // Run the actual service method
        Tag foundTag = tagService.getTagByName("Test Tag");

        // Verify that the findTagByName method was called once with name "Test Tag"
        verify(tagRepository, times(1)).findTagByName("Test Tag");

        // Assert that the returned tag matches the mock tag
        assertEquals(testTag, foundTag);
    }

    @Test
    public void testUpdateTag() throws Exception {
        // Create updated tag details
        Tag updatedTag = new Tag(1, "Updated Tag", "Updated Description", null);

        // Mock returning the updated tag when saved
        when(tagRepository.findById(1)).thenReturn(Optional.of(testTag));
        when(tagRepository.save(any(Tag.class))).thenReturn(updatedTag);

        // Run the actual service method
        Tag result = tagService.updateTag(1, updatedTag);

        // Verify that the save method was called once with the updated tag
        verify(tagRepository, times(1)).save(updatedTag);

        // Assert that the returned tag matches the updated tag
        assertEquals(updatedTag, result);
    }

    @Test
    public void testDeleteTag() throws Exception {
        // Mock returning the tag when searched by ID
        when(tagRepository.findById(1)).thenReturn(Optional.of(testTag));

        // Run the actual service method
        tagService.deleteTag(1);

        // Verify that the delete method was called once with the mock tag
        verify(tagRepository, times(1)).delete(testTag);
    }
}