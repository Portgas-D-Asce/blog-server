package com.devil.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Tag;
import com.devil.blog.service.TagService;
import com.devil.blog.service.TagServiceImpl;

@CrossOrigin
@RestController
public class TagController {
    @Autowired
    private TagService tagService = new TagServiceImpl();

    @GetMapping("/api/v1/tags")
    public List<Tag> getTags(@RequestParam(required = false) Integer article_id) {
        if(article_id == null) {
            article_id = -1;
        }
        return tagService.getTags(article_id);
    }

    @GetMapping("/api/v1/tags/{id}")
    public Tag getTag(@PathVariable("id") Integer id) {
        Tag tag = tagService.getTag(id);
        return tag;
    }

    @PutMapping("/api/v1/tags/{id}")
    public boolean updateTag(@PathVariable("id") Integer id, @RequestBody Map<String, Object> map) {
        return tagService.updateTag(id, map);
    }

    @PostMapping("/api/v1/tags")
    public int insertTag(@RequestBody Map<String, Object> map) {
        //Object obj_aids = map.get("aids");

        //Tag tag = objectMapper.readValue(objectMapper.writeValueAsString(obj_tag), Tag.class);
        //CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(
        //    ArrayList.class, Integer.class);
        //List<Integer> aids = objectMapper.readValue(objectMapper.writeValueAsString(obj_aids), listType);
        //return tagService.insertTag(tag, aids);
        return tagService.insertTag(map);
    }

    @DeleteMapping("/api/v1/tags/{id}")
    public boolean deleteTag(@PathVariable("id") Integer id) {
        return tagService.deleteTag(id);
    }

    @GetMapping("/api/v1/tags/statistics")
    public List<Map<String, Object>> getTags() {
        return tagService.getTagStatistics();
    }
}
