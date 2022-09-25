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
import com.devil.blog.mapper.TagMapper;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
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

        List<Article> articles = articleMapper.getArticles(ids);
        System.out.println(articles.toString());


        List<Tag> tags = tagMapper.queryTags();
        System.out.println(tags.toString());
        HashMap<Integer, Tag> hmp_tags = new HashMap<>();
        for(Tag tag : tags) {
            hmp_tags.put(tag.getId(), tag);
        }

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
            articleAbstract.setTags(new ArrayList<>());
            String[] tag_ids = article.getTids().split(",");
            for(String tag_id : tag_ids) {
                articleAbstract.getTags().add(hmp_tags.get(Integer.parseInt(tag_id)));
            }
            abstracts.add(articleAbstract);
        }
        return abstracts;
    }

    @Override
    public Category getCategory(int id) {
        Category category = categoryMapper.getCategoryById(id);
        return category;
    }

    @Override
    public CategoryTree getTree(int id) {
        List<Category> categories = categoryMapper.queryCategories();
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
}
