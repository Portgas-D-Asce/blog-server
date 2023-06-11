package com.devil.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Category;
import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public List<ArticleAbstract> getAbstracts(int id) {
        Category root = getCategory(id, true);
        Queue<Category> que = new LinkedList<>();
        que.add(root);
        List<Integer> ids = new ArrayList<>();
        while(!que.isEmpty()) {
            Category u = que.peek();
            ids.add(u.getId());
            for(Category v : u.getChildren()) {
                que.add(v);
            }
            que.poll();
        }
        System.out.println(ids.toString());

        List<Article> articles = categoryMapper.getArticles(ids);
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
    public boolean updateCategory(int id, Map<String, Object> map) {
        return categoryMapper.updateCategory(id, map);
    }

    @Override
    public Category getCategory(int id, Boolean recursion) {
        if(recursion == false) {
            Category category = categoryMapper.getCategory(id);
            System.out.println(category.toString());
            return category;
        }

        List<Category> categories = categoryMapper.getCategories();
        Category root = null;
        HashMap<Integer, Category> hmp = new HashMap<>();
        for(Category category : categories) {
            category.setChildren(new ArrayList<Category>());
            hmp.put(category.getId(), category);
            if(category.getId() == id) {
                root = category;
            }
        }
        for(Category category : categories) {
            int cid = category.getId();
            int pid = category.getPid();
            if(cid == pid) continue;
            hmp.get(pid).getChildren().add(hmp.get(cid));
        }
        return root;
    }

    @Override
    public int insertCategory(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        categoryMapper.insertCategory(map);
        String sid = map.get("id").toString();
        return Integer.parseInt(sid);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryMapper.deleteCategory(id);
    }

}
