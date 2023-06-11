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

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BindMapper bindMapper;

    @Override
    public List<ArticleAbstract> getAbstractsByCategoryIds(List<Integer> ids) {
                System.out.println(ids.toString());

        List<Article> articles = articleMapper.getArticlesByCategoryIds(ids);
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
    public List<ArticleAbstract> getAbstractsByTagId(int id) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
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
    public Article getArticle(int id) {
        Article article = articleMapper.getArticle(id);
        return article;
    }

    @Override
    public boolean updateArticle(int id, Map<String, Object> map) {
        return articleMapper.updateArticle(id, map);
    }

    @Override
    public boolean updateContent(int id, String name, byte[] content) {
        return articleMapper.updateContent(id, name, content);
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
