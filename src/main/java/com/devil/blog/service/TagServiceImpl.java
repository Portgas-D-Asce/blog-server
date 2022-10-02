package com.devil.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.TagMapper;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Tag> getTags() {
        List<Tag> tags = tagMapper.getTags();
        return tags;
    }

    @Override
    public Tag getTag(int id) {
        Tag tag = tagMapper.getTag(id);
        return tag;
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

}
