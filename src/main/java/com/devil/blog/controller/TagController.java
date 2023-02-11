package com.devil.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
