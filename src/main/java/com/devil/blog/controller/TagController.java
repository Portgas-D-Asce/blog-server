package com.devil.blog.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Tag;
import com.devil.blog.service.TagService;
import com.devil.blog.service.TagServiceImpl;

@CrossOrigin
@RestController
public class TagController {
    @Autowired
    private TagService tagService = new TagServiceImpl();

    @GetMapping("/api/v1/tags/{name}")
    public ResponseEntity<Object> getTag(@PathVariable("name") String name) {
        Tag tag = tagService.getTag(name);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @GetMapping("/api/v1/tags")
    public ResponseEntity<Object> getTags() {
        List<Tag> tags = tagService.getTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    //needn't provid this???
    //@GetMapping("/api/v1/tags")
    //public ResponseEntity<Object> getTags(@RequestParam(required = false) Integer article_id) {
    //    List<Tag> tags;
    //    if(article_id == null) {
    //        tags = tagService.getAllTags();
    //    } else {
    //        tags = tagService.getTagsByArticleId(article_id);
    //    }
    //    return new ResponseEntity<>(tags, HttpStatus.OK);
    //}

    @PutMapping("/api/v1/tags/{name}")
    public ResponseEntity<Object> updateTag(@PathVariable("name") Integer id,
                                            @RequestBody Map<String, Object> map) {
        Tag tag = tagService.updateTag(id, map);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @PutMapping("/api/v1/tags")
    public ResponseEntity<Object> updateTags(@RequestBody List<Map<String, Object>> maps) {
        List<Tag> tags = tagService.updateTags(maps);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("/api/v1/tags")
    public ResponseEntity<Object> insertTags(@RequestBody List<Map<String, Object>> maps) {
        List<Tag> tags = tagService.insertTags(maps);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/tags/{name}")
    public ResponseEntity<Object> deleteTag(@PathVariable("name") String name) {
        Integer cnt = tagService.deleteTag(name);
        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/tags")
    public ResponseEntity<Object> deleteTags() {
        Integer cnt = tagService.deleteTags();
        return new ResponseEntity<>(cnt, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/v1/tags/statistics")
    public ResponseEntity<Object> getTagsStatistics() {
        List<Map<String, Object>> sts =  tagService.getStatistics();
        return new ResponseEntity<>(sts, HttpStatus.OK);
    }
}
