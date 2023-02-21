package com.devil.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.BindMapper;
import com.devil.blog.mapper.TagMapper;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BindMapper bindMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Map<String, Object>> getTags() {
        return tagMapper.getTags();
    }

    @Override
    public Tag getTag(int id) {
        Tag tag = tagMapper.getTag(id);
        return tag;
    }

    @Override
    public boolean updateTag(int id, Map<String, Object> map) {
        return tagMapper.updateTag(id, map);
    }

    @Override
    public List<ArticleAbstract> getAbstracts(int id) {
        List<Article> articles = tagMapper.getArticles(id);
        System.out.println(articles.toString());
        List<ArticleAbstract> abstracts = new ArrayList<>();
        for(Article article : articles) {
            ArticleAbstract articleAbstract = new ArticleAbstract();
            articleAbstract.setId(article.getId());
            articleAbstract.setName(article.getName());
            articleAbstract.setDescription(article.getDescription());
            articleAbstract.setDate(article.getDate());
            articleAbstract.setRead(article.getRead());
            articleAbstract.setUpvoted(article.getUpvoted());
            articleAbstract.setDownvoted(article.getDownvoted());
            List<Tag> tags = articleMapper.getTags(article.getId());
            articleAbstract.setTags(tags);
            abstracts.add(articleAbstract);
        }
        return abstracts;
    }

    @Override
    @Transactional
    public int insertTag(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        tagMapper.insertTag(map);
        String sid = map.get("id").toString();
        return Integer.parseInt(sid);
    }

    @Override
    @Transactional
    public boolean deleteTag(int id) {
        bindMapper.unbindArticles(id);
        return tagMapper.deleteTag(id);
    }
}
