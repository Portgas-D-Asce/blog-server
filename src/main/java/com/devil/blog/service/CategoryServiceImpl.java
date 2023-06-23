package com.devil.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Category;
import com.devil.blog.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category getCategory(int id) {
        Category category = categoryMapper.getCategory(id);
        return category;
    }

    @Override
    public Category getCategoryRecurively(int id) {
        List<Category> categories = categoryMapper.getAllCategories();
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
    public boolean updateCategoryRecursively(int id, Map<String, Object> map) {
        return categoryMapper.updateCategory(id, map);
    }

    @Override
    public int insertCategoryRecursively(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        map.put("params", params);
        categoryMapper.insertCategory(map);
        String sid = map.get("id").toString();
        return Integer.parseInt(sid);
    }

    @Override
    public boolean deleteCategoryRecursively(int id) {
        return categoryMapper.deleteCategory(id);
    }

    @Override
    public List<Integer> getDescendants(int id) {
        Category root = getCategoryRecurively(id);
        Queue<Category> que = new LinkedList<>();
        que.add(root);
        List<Integer> descendants = new ArrayList<>();
        while(!que.isEmpty()) {
            Category u = que.peek();
            descendants.add(u.getId());
            for(Category v : u.getChildren()) {
                que.add(v);
            }
            que.poll();
        }

        return descendants;
    }
}
