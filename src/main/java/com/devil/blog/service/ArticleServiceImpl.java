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
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.TagMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Article getArticle(int id) {
        Article article = articleMapper.getArticle(id);
        return fillArticle(article, true);
    }

    @Override
    public Article getArticleByName(String name) {
        Article article = articleMapper.getArticleByName(name);
        return fillArticle(article, true);
    }

    @Override
    public List<Article> getAllArticles(Boolean with_content) {
        List<Article> articles = articleMapper.getAllArticles();
        return fillArticles(articles, with_content);
    }

    @Override
    public List<Article> getArticlesByCategoryId(int id, Boolean with_content) {
        List<Article> articles = articleMapper.getArticlesByCategoryId(id);
        return fillArticles(articles, with_content);
    }

    @Override
    public List<Article> getArticlesByTagId(int id, Boolean with_content) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        return fillArticles(articles, with_content);
    }

    @Override
    @Transactional
    public Article updateArticle(int id, Map<String, Object> map) {
        if(map.containsKey("tags")) {
            String tags = String.valueOf(map.get("tags"));
            map.remove("tags");

            bindTags(id, tags);
        }
        articleMapper.updateArticle(id, map);
        return getArticle(id);

    }

    @Override
    @Transactional
    public Article insertArticle(Map<String, Object> params) {
        String tags = null;
        if(params.containsKey("tags")) {
            tags = String.valueOf(params.get("tags"));
            params.remove("tags");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        articleMapper.insertArticle(map);

        int id = Integer.parseInt(map.get("id").toString());
        if(tags != null) {
            bindTags(id, tags);
        }
        return getArticle(id);
    }

    @Override
    @Transactional
    public int deleteArticle(int id) {
        articleMapper.unbindTags(id);

        int res = articleMapper.deleteArticle(id);
        return res;
    }

    private Article fillArticle(Article article, Boolean with_content) {
        if(with_content == false) {
            article.setContent(null);
        }
        List<Tag> tags = tagMapper.getTagsByArticleId(article.getId());
        article.setTags(tags);
        return article;
    }

    private List<Article> fillArticles(List<Article> articles, Boolean with_content) {
        for (Article article : articles) {
            fillArticle(article, with_content);
        }
        return articles;
    }

    private boolean bindTags(int id, String tags) {
        List<Integer> tag_ids = new ArrayList<>();
        String items[] = tags.split(",");
        for (String item : items) {
            tag_ids.add(Integer.parseInt(item));
        }
        articleMapper.unbindTags(id);
        articleMapper.bindTags(id, tag_ids);
        return true;
    }
}
