package com.devil.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devil.blog.entity.Article;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.BindMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BindMapper bindMapper;

    @Override
    public Article getArticle(int id) {
        Article article = articleMapper.getArticle(id);
        return article;
    }

    @Override
    @Transactional
    public int insertArticle(Article article, List<Integer> tids) {
        articleMapper.insertArticle(article);
        int aid = article.getId();
        if(tids != null && tids.size() > 0) {
            bindMapper.bindTags(aid, tids);
        }
        return aid;
    }

    @Override
    @Transactional
    public boolean deleteArticle(int id) {
        bindMapper.unbindTags(id);

        boolean res = articleMapper.deleteArticle(id);
        return res;
    }

    @Override
    public boolean updateArticle(int id, String name, byte[] content) {
        return articleMapper.updateArticle(id, name, content);
    }
}
