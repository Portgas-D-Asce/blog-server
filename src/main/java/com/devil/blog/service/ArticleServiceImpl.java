package com.devil.blog.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devil.blog.entity.Category;
import com.devil.blog.mapper.CategoryMapper;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Article getArticle(Integer id, Boolean withContent) {
        Article article = articleMapper.getArticle(id);
        fillArticle(article, withContent);
        return article;
    }

    @Override
    public Article getArticleByName(String name, Boolean withContent) {
        Article article = articleMapper.getArticleByName(name);
        fillArticle(article, withContent);
        return article;
    }

    @Override
    public List<Article> getAllArticles(Boolean withContent) {
        List<Article> articles = articleMapper.getAllArticles();
        fillArticles(articles, withContent);
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryId(Integer id, Boolean withContent) {
        List<Article> articles = articleMapper.getArticlesByCategoryId(id);
        fillArticles(articles, withContent);
        return articles;
    }

    @Override
    public List<Article> getArticlesByCategoryIdRecursively(int id, boolean withContent) {
        Category root = categoryMapper.getCategory(id);
        List<Category> descendants = categoryMapper.getDescendantCategories(root.relPath());
        descendants.add(root);

        List<Article> articles = new ArrayList<>();
        for(Category descendant : descendants) {
            articles.addAll(getArticlesByCategoryId(descendant.getId(), withContent));
        }

        return articles;
    }

    @Override
    public List<Article> getArticlesByTagId(Integer id, Boolean withContent) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        fillArticles(articles, withContent);
        return articles;
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

        if(map.containsKey("content")) {
            String old_content = new String((byte[])map.get("content"), StandardCharsets.UTF_8);
            String prefix = "/blog/api/v1/images?name=" + id.toString() + "-";
            String content = old_content.replaceAll("images/", prefix);
            map.put("content", content.getBytes());
        }

        if(!map.isEmpty()) {
            articleMapper.updateArticle(id, map);
        }

        return getArticle(id, true);
    }

    @Override
    @Transactional
    public Article insertArticle(Map<String, Object> params) {
        String tags = String.valueOf(params.get("tags"));
        params.remove("tags");
        List<Map<String, Object>> images = (List<Map<String, Object>>)params.get("images");
        params.remove("images");

        // insert article
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        articleMapper.insertArticle(map);
        int id = Integer.parseInt(map.get("id").toString());

        bindTags(id, tags);

        addImages(id, images);

        // update article images
        Map<String, Object> temp = new HashMap<>();
        temp.put("content", params.get("content"));
        updateArticle(id, temp);

        return getArticle(id, true);
    }

    @Override
    @Transactional
    public Integer deleteArticle(Integer id) {
        articleMapper.unbindTags(id);

        imageMapper.deleteImagesByArticleId(id + "-");

        return articleMapper.deleteArticle(id);
    }

    //@Override
    //@Transactional
    //public Integer deleteArticleByName(String name) {
    //    Article article = articleMapper.getArticleByName(name);
    //    return deleteArticle(article.getId());
    //}

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
    public int deleteArticlesByCategoryIdRecursively(int id) {
        Category root = categoryMapper.getCategory(id);
        List<Category> descendants = categoryMapper.getDescendantCategories(root.relPath());
        descendants.add(root);

        int cnt = 0;
        for(Category descendant : descendants) {
            cnt += deleteArticlesByCategoryId(descendant.getId());
        }
        return cnt;
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

    private void fillArticle(Article article, Boolean withContent) {
        if(!withContent) {
            article.setContent(null);
        }
        List<Tag> tags = tagMapper.getTagsByArticleId(article.getId());
        article.setTags(tags);
    }

    private void fillArticles(List<Article> articles, Boolean with_content) {
        for (Article article : articles) {
            fillArticle(article, with_content);
        }
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
