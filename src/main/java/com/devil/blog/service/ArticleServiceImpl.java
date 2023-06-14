package com.devil.blog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Tag;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.BindMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BindMapper bindMapper;

    @Override
    public List<Article> getArticlesByCategoryIds(List<Integer> ids, Boolean with_content) {
        List<Article> articles = articleMapper.getArticlesByCategoryIds(ids);
        for(Article article : articles) {
            if(with_content == false) {
                article.setContent(null);
            }
            List<Tag> tags = articleMapper.getTags(article.getId());
            article.setTags(tags);
        }
        return articles;
    }

    @Override
    public List<Article> getArticlesByTagId(int id, Boolean with_content) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        for(Article article : articles) {
            if(with_content == false) {
                article.setContent(null);
            }
            List<Tag> tags = articleMapper.getTags(article.getId());
            article.setTags(tags);
        }
        return articles;
    }

    @Override
    public Article getArticle(int id) {
        Article article = articleMapper.getArticle(id);
        return article;
    }

    @Override
    public boolean updateArticle(int id, Map<String, Object> map) {
        return articleMapper.updateArticle(id, map);
    }

    @Override
    public int insertArticle(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        articleMapper.insertArticle(map);
        String sid = map.get("id").toString();
        return Integer.parseInt(sid);
    }

    @Override
    @Transactional
    public boolean deleteArticle(int id) {
        bindMapper.unbindTags(id);

        boolean res = articleMapper.deleteArticle(id);
        return res;
    }
}
