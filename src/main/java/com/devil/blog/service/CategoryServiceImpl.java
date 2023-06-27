package com.devil.blog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.devil.blog.entity.Category;
import com.devil.blog.mapper.CategoryMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category getCategory(int id) {
        return categoryMapper.getCategory(id);
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

    //@Override
    //public boolean updateCategoryRecursively(int id, Map<String, Object> map) {
    //    return categoryMapper.updateCategory(id, map);
    //}

    @Override
    @Transactional
    public Category insertCategoryRecursively(Map<String, Object> params) {
        int res = 0;
        //map to object
        Category root = JSONObject.parseObject(JSONObject.toJSONString(params), Category.class);
        //add from top to bottom
        List<Category> descendants = getDescendants(root);
        for (Category category : descendants) {
            category.setChildren(new ArrayList<>());
            //object to map
            Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(category));
            data.remove("children");
            Map<String, Object> map = new HashMap<>();
            map.put("params", data);

            res = categoryMapper.insertCategory(map);
            if(res != 1) {
                break;
            }
        }
        return getCategoryRecurively(root.getId());
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryMapper.deleteCategory(id);
    }

    @Override
    @Transactional
    public boolean deleteCategoryRecursively(int id) {
        //delete from bottom to top
        Category root = getCategoryRecurively(id);
        List<Category> descendants = getDescendants(root);
        Collections.reverse(descendants);
        for (Category category : descendants) {
            if(categoryMapper.deleteCategory(category.getId()) == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Category> getDescendants(Category root) {
        List<Category> descendants = new ArrayList<>();
        Queue<Category> que = new LinkedList<>();
        que.add(root);
        while(!que.isEmpty()) {
            Category u = que.peek();
            descendants.add(u);
            que.poll();
            if(u.getChildren() == null) {
                continue;
            }
            for(Category v : u.getChildren()) {
                que.add(v);
            }
        }

        return descendants;
    }
}
