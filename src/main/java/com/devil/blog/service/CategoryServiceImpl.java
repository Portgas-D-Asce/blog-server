package com.devil.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Category;
import com.devil.blog.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

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
