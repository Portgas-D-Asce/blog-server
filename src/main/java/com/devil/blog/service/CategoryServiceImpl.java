package com.devil.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Article;
import com.devil.blog.entity.Category;
import com.devil.blog.entity.Tag;
import com.devil.blog.entity.model.ArticleAbstract;
import com.devil.blog.entity.model.CategoryTree;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public List<ArticleAbstract> getAbstracts(int id) {
        CategoryTree root = getTree(id);
        Queue<CategoryTree> que = new LinkedList<>();
        que.add(root);
        List<Integer> ids = new ArrayList<>();
        while(!que.isEmpty()) {
            CategoryTree u = que.peek();
            ids.add(u.getId());
            for(CategoryTree v : u.getChildren()) {
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
    public Category getCategory(int id) {
        Category category = categoryMapper.getCategory(id);
        return category;
    }

    @Override
    public CategoryTree getTree(int id) {
        List<Category> categories = categoryMapper.getCategories();
        CategoryTree root = null;
        HashMap<Integer, CategoryTree> hmp = new HashMap<>();
        for(Category category : categories) {
            CategoryTree node = new CategoryTree(category.getId(), category.getName(),
            category.getDescription(), new ArrayList<CategoryTree>());
            hmp.put(category.getId(), node);
            if(node.getId() == id) {
                root = node;
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
    public int insertCategory(Category category) {
        return categoryMapper.insertCategory(category);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryMapper.deleteCategory(id);
    }

}
