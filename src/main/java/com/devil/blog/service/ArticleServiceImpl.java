package com.devil.blog.service;

import java.nio.charset.StandardCharsets;
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
import com.devil.blog.mapper.ImageMapper;
import com.devil.blog.mapper.TagMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public Article getArticle(Integer id, Boolean withContent) {
        Article article = articleMapper.getArticle(id);
        return fillArticle(article, withContent);
    }

    @Override
    public Article getArticleByName(String name, Boolean withContent) {
        Article article = articleMapper.getArticleByName(name);
        return fillArticle(article, withContent);
    }

    @Override
    public List<Article> getAllArticles(Boolean with_content) {
        List<Article> articles = articleMapper.getAllArticles();
        return fillArticles(articles, with_content);
    }

    @Override
    public List<Article> getArticlesByCategoryId(Integer id, Boolean with_content) {
        List<Article> articles = articleMapper.getArticlesByCategoryId(id);
        return fillArticles(articles, with_content);
    }

    @Override
    public List<Article> getArticlesByTagId(Integer id, Boolean with_content) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        return fillArticles(articles, with_content);
    }

    @Override
    @Transactional
    public Article updateArticle(Integer id, Map<String, Object> map) {
        if(map.containsKey("tags")) {
            String tags = String.valueOf(map.get("tags"));
            map.remove("tags");

            bindTags(id, tags);
        }

        if(map.containsKey("images")) {
            List<Map<String, Object>> images = (List<Map<String, Object>>)map.get("images");
            map.remove("images");

            addImages(id, images);
        }

        String old_content = new String((byte[])map.get("content"), StandardCharsets.UTF_8);
        String prefix = "/blog/api/v1/images?name=" + id.toString() + "-";
        String content = old_content.replaceAll("images/", prefix);
        map.put("content", content.getBytes());

        articleMapper.updateArticle(id, map);
        return getArticle(id, true);

    }

    @Override
    @Transactional
    public Article insertArticle(Map<String, Object> params) {
        String tags = null;
        if(params.containsKey("tags")) {
            tags = String.valueOf(params.get("tags"));
            params.remove("tags");
        }
        List<Map<String, Object>> images = null;
        if(params.containsKey("images")) {
            images = (List<Map<String, Object>>)params.get("images");
            params.remove("images");
        }
        // insert article
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        articleMapper.insertArticle(map);
        Integer id = Integer.parseInt(map.get("id").toString());

        // update article images
        String old_content = new String((byte[])params.get("content"), StandardCharsets.UTF_8);
        String prefix = "/blog/api/v1/images?name=" + id.toString() + "-";
        String content = old_content.replaceAll("images/", prefix);
        Map<String, Object> temp = new HashMap<>();
        temp.put("content", content.getBytes());
        articleMapper.updateArticle(id, temp);

        // bind tags
        if(tags != null) {
            bindTags(id, tags);
        }

        // insert images
        if(images != null) {
            addImages(id, images);
        }
        return getArticle(id, true);
    }

    @Override
    @Transactional
    public Integer deleteArticle(Integer id) {
        articleMapper.unbindTags(id);

        imageMapper.deleteImagesByArticleId(id + "-");

        return articleMapper.deleteArticle(id);
    }

    @Override
    @Transactional
    public Integer deleteArticleByName(String name) {
        Article article = articleMapper.getArticleByName(name);

        return deleteArticle(article.getId());
    }

    /*I'm a lazy boy*/
    @Override
    @Transactional
    public Integer deleteAllArticles() {
        List<Article> articles = articleMapper.getAllArticles();
        for(Article article : articles) {
            deleteArticle(article.getId());
        }
        return articles.size();
    }

    @Override
    @Transactional
    public Integer deleteArticlesByCategoryId(Integer id) {
        List<Article> articles = articleMapper.getArticlesByCategoryId(id);
        for(Article article : articles) {
            deleteArticle(article.getId());
        }
        return articles.size();
    }

    @Override
    @Transactional
    public Integer deleteArticlesByTagId(Integer id) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        for(Article article : articles) {
            deleteArticle(article.getId());
        }
        return articles.size();
    }

    private Article fillArticle(Article article, Boolean withContent) {
        if(!withContent) {
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

    private boolean addImages(Integer id, List<Map<String, Object>> images) {
        String extra = id.toString() + "-";
        imageMapper.deleteImagesByArticleId(extra);
        for (Map<String,Object> image : images) {
            image.put("name", extra + String.valueOf(image.get("name")));
            Map<String, Object> wapper = new HashMap<>();
            wapper.put("params", image);
            imageMapper.insertImage(wapper);
        }
        return true;
    }
}
