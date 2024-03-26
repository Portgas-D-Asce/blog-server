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
    public Article getArticle(String name, Boolean withContent) {
        Article article = articleMapper.getArticleByName(name);
        fillArticle(article, withContent);
        return article;
    }

    @Override
    public List<Article> getArticles(Boolean withContent) {
        List<Article> articles = articleMapper.getArticles();
        fillArticles(articles, withContent);
        return articles;
    }

    public List<Article> getCategoryArticles(int id, Boolean withContent) {
        List<Article> articles = articleMapper.getArticlesByCategoryId(id);
        fillArticles(articles, withContent);
        return articles;
    }

    @Override
    public List<Article> getCategoryArticles(String name, Boolean withContent) {
        Category category = categoryMapper.getCategoryByName(name);
        return getCategoryArticles(category.getId(), withContent);
    }

    @Override
    public List<Article> getCategoryArticlesRecursively(String name, boolean withContent) {
        Category root = categoryMapper.getCategoryByName(name);
        List<Category> descendants = categoryMapper.getDescendantCategories(root.relPath());
        descendants.add(root);

        List<Article> articles = new ArrayList<>();
        for(Category descendant : descendants) {
            articles.addAll(getCategoryArticles(descendant.getId(), withContent));
        }

        return articles;
    }

    @Override
    public List<Article> getTagArticles(int id, Boolean withContent) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        fillArticles(articles, withContent);
        return articles;
    }

    @Override
    public List<Article> getTagArticles(String name, Boolean withContent) {
        Tag tag = tagMapper.getTagByName(name);
        return getTagArticles(tag.getId(), withContent);
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
            //String prefix = "https://www.xdevil.top:8080/blog/api/v1/images?name=" + id.toString() + "-";
            String prefix = "/blog/api/v1/images?name=" + id.toString() + "-";
            String content = old_content.replaceAll("images/", prefix);
            map.put("content", content.getBytes());
        }

        if(map.containsKey("category")) {
            String category_name = String.valueOf(map.get("category"));
            map.remove("category");
            Category category = categoryMapper.getCategoryByName(category_name);
            map.put("cid", category.getId());
        }

        if(!map.isEmpty()) {
            articleMapper.updateArticle(id, map);
        }

        return getArticle(id, true);
    }

    @Override
    @Transactional
    public Article updateArticle(String name, Map<String, Object> map) {
        Article article = articleMapper.getArticleByName(name);
        return updateArticle(article.getId(), map);
    }

    @Override
    @Transactional
    public Article insertArticle(Map<String, Object> params) {
        String tags = String.valueOf(params.get("tags"));
        params.remove("tags");
        List<Map<String, Object>> images = (List<Map<String, Object>>)params.get("images");
        params.remove("images");

        String category_name = String.valueOf(params.get("category"));
        Category category = categoryMapper.getCategoryByName(category_name);
        params.remove("category");
        params.put("cid", category.getId());

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
    public Integer deleteArticle(int id) {
        articleMapper.unbindTags(id);

        imageMapper.deleteImagesByArticleId(id + "-");

        return articleMapper.deleteArticle(id);
    }

    @Override
    @Transactional
    public Integer deleteArticle(String name) {
        Article article = articleMapper.getArticleByName(name);
        return deleteArticle(article.getId());
    }

    @Override
    @Transactional
    public Integer deleteArticles() {
        List<Article> articles = articleMapper.getArticles();
        for(Article article : articles) {
            deleteArticle(article.getId());
        }
        return articles.size();
    }

    @Override
    @Transactional
    public Integer deleteCategoryArticles(int id) {
        List<Article> articles = articleMapper.getArticlesByCategoryId(id);
        for(Article article : articles) {
            deleteArticle(article.getId());
        }
        return articles.size();
    }

    @Override
    @Transactional
    public Integer deleteCategoryArticles(String name) {
        Category category = categoryMapper.getCategoryByName(name);
        return deleteCategoryArticles(category.getId());
    }

    @Override
    @Transactional
    public Integer deleteCategoryArticlesRecursively(String name) {
        Category root = categoryMapper.getCategoryByName(name);
        List<Category> descendants = categoryMapper.getDescendantCategories(root.relPath());
        descendants.add(root);

        int cnt = 0;
        for(Category descendant : descendants) {
            cnt += deleteCategoryArticles(descendant.getId());
        }
        return cnt;
    }

    @Override
    @Transactional
    public Integer deleteTagArticles(int id) {
        List<Article> articles = articleMapper.getArticlesByTagId(id);
        for(Article article : articles) {
            deleteArticle(article.getId());
        }
        return articles.size();
    }

    @Override
    @Transactional
    public Integer deleteTagArticles(String name) {
        Tag tag = tagMapper.getTagByName(name);
        return deleteTagArticles(tag.getId());
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
        List<Integer> tagIds = new ArrayList<>();
        String[] names = tags.split(",");
        for (String name : names) {
            Tag tag = tagMapper.getTagByName(name);
            tagIds.add(tag.getId());
        }
        articleMapper.unbindTags(id);
        articleMapper.bindTags(id, tagIds);
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
