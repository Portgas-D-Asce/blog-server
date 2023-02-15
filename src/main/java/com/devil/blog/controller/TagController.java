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
import org.springframework.web.bind.annotation.RestController;

import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.service.TagService;
import com.devil.blog.service.TagServiceImpl;

@CrossOrigin
@RestController
public class TagController {
    @Autowired
    private TagService tagService = new TagServiceImpl();

    @GetMapping("/tag")
    public List<Tag> getTags() {
        List<Tag> tags = tagService.getTags();
        return tags;
    }

    @GetMapping("/tag/{id}")
    public Tag getTag(@PathVariable("id") int id) {
        Tag tag = tagService.getTag(id);
        return tag;
    }

    @PutMapping("/tag/{id}")
    public boolean updateTag(@PathVariable("id") int id, @RequestBody Map<String, Object> map) {
        return tagService.updateTag(id, map);
    }

    @GetMapping("/tag/{id}/abstract")
    public List<ArticleAbstract> getAbstracts(@PathVariable("id") int id) {
        List<ArticleAbstract> abstracts = tagService.getAbstracts(id);
        return abstracts;
    }

    @PostMapping("/tag")
    public int insertTag(@RequestBody Map<String, Object> map) {
        //Object obj_aids = map.get("aids");

        //Tag tag = objectMapper.readValue(objectMapper.writeValueAsString(obj_tag), Tag.class);
        //CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(
        //    ArrayList.class, Integer.class);
        //List<Integer> aids = objectMapper.readValue(objectMapper.writeValueAsString(obj_aids), listType);
        //return tagService.insertTag(tag, aids);
        return tagService.insertTag(map);
    }

    @DeleteMapping("/tag/{id}")
    public boolean deleteTag(@PathVariable("id") int id) {
        return tagService.deleteTag(id);
    }
}
