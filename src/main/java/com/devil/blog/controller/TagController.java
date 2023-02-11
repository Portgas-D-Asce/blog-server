package com.devil.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/tag/{id}/abstract")
    public List<ArticleAbstract> getAbstracts(@PathVariable("id") int id) {
        List<ArticleAbstract> abstracts = tagService.getAbstracts(id);
        return abstracts;
    }

    @PostMapping("/tag")
    public Tag insertTag() {
        return new Tag();
    }

    @DeleteMapping("/tag/{id}")
    public boolean deleteTag(@PathVariable("id") int id) {
        return tagService.deleteTag(id);
    }

    @PutMapping("/tag/{id}")
    public Tag updateTag(@PathVariable("id") int id) {
        return new Tag();
    }
}
