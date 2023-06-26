package com.devil.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/api/v1/tags/{id}")
    public ResponseEntity<Object> getTag(@PathVariable("id") Integer id) {
        Tag tag = tagService.getTag(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/api/v1/tags")
    public ResponseEntity<Object> getTags(@RequestParam(required = false) Integer article_id) {
        List<Tag> tags = new ArrayList<>();
        if(article_id == null) {
            tags = tagService.getAllTags();
        } else {
            tags = tagService.getTagsByArticleId(article_id);
        }
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PutMapping("/api/v1/tags/{id}")
    public ResponseEntity<Object> updateTag(@PathVariable("id") Integer id, @RequestBody Map<String, Object> map) {
        Boolean res = tagService.updateTag(id, map);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/api/v1/tags")
    public ResponseEntity<Object> insertTag(@RequestBody Map<String, Object> map) {
        //Object obj_aids = map.get("aids");

        //Tag tag = objectMapper.readValue(objectMapper.writeValueAsString(obj_tag), Tag.class);
        //CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(
        //    ArrayList.class, Integer.class);
        //List<Integer> aids = objectMapper.readValue(objectMapper.writeValueAsString(obj_aids), listType);
        //return tagService.insertTag(tag, aids);
        Integer id = tagService.insertTag(map);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/tags/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") Integer id, @RequestParam(required = false) String force) {
        Boolean res = false;
        if(force != null && force.equals("true")) {
            res = tagService.deleteTagForcely(id);
        } else {
            res = tagService.deleteTag(id);
        }
        return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/v1/tags/statistics")
    public ResponseEntity<Object> getTagsStatistics() {
        List<Map<String, Object>> sts =  tagService.getTagsStatistics();
        return new ResponseEntity<>(sts, HttpStatus.OK);
    }
}
