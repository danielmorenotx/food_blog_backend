package com.example.foodblog.controller;

import com.example.foodblog.model.Tag;
import com.example.foodblog.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
@AutoConfigureMockMvc
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    private Tag testTag = Tag.builder()
            .id(1)
            .name("tag1")
            .description("tag description")
            .build();

    @Test
    public void testAddTag() throws Exception {
        when(tagService.addTag(any(Tag.class))).thenReturn(testTag);
        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTag)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTags() throws Exception {
        List<Tag> testTags = new ArrayList<>();
        testTags.add(new Tag(1, "Tag 1", "Description 1", null));
        testTags.add(new Tag(2, "Tag 2", "Description 2", null));
        when(tagService.getAllTags()).thenReturn(testTags);
        mockMvc.perform(get("/tags/all-tags"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTagById() throws Exception {
        when(tagService.getTagById(1)).thenReturn(testTag);
        mockMvc.perform(get("/tags/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTagByName() throws Exception {
        when(tagService.getTagByName("Test Tag")).thenReturn(testTag);
        mockMvc.perform(get("/tags/by-name").param("name", "Test Tag"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTag() throws Exception {
        when(tagService.updateTag(1, testTag)).thenReturn(testTag);
        mockMvc.perform(put("/tags/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testTag)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTag() throws Exception {
        mockMvc.perform(delete("/tags/{id}", 1))
                .andExpect(status().isOk());
        verify(tagService, times(1)).deleteTag(1);
    }
}

