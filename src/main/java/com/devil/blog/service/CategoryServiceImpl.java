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
        Category category = categoryMapper.getCategory(id);
        category.setChildren(new ArrayList<>());
        return category;
    }

    @Override
    public Category getCategory(String name) {
        Category category = categoryMapper.getCategoryByName(name);
        category.setChildren(new ArrayList<>());
        return category;
    }

    @Override
    public Category getCategoryRecursively(int id) {
        Category root = getCategory(id);
        // had sorted at sql query
        List<Category> descendants = categoryMapper.getDescendantCategories(root.relPath());

        HashMap<Integer, Category> hmp = new HashMap<>();
        hmp.put(id, root);
        for(Category descendant : descendants) {
            descendant.setChildren(new ArrayList<>());
            int cid = descendant.getId();
            hmp.put(cid, descendant);
            int pid = descendant.getPid();
            hmp.get(pid).getChildren().add(descendant);
        }

        return root;
    }

    @Override
    public Category getCategoryRecursively(String name) {
        Category root = getCategory(name);
        return getCategoryRecursively(root.getId());
    }

    //@Override
    //public boolean updateCategoryRecursively(int id, Map<String, Object> map) {
    //    return categoryMapper.updateCategory(id, map);
    //}

    @Override
    @Transactional
    public Category insertCategoryTree(int pid, Map<String, Object> params) {
        Category root = getCategory(pid);
        Category tree = JSONObject.parseObject(JSONObject.toJSONString(params), Category.class);
        root.getChildren().add(tree);

        Queue<Category> que = new LinkedList<>();
        que.add(root);
        while(!que.isEmpty()) {
            Category cur = que.peek();
            que.poll();

            List<Category> children = cur.getChildren();
            if(children == null) continue;

            for(Category child : children) {
                child.setPid(cur.getId());
                child.setPath(cur.relPath());

                List<Category> temp = child.getChildren();
                child.setChildren(null);
                Map<String, Object> data = JSONObject.parseObject(JSONObject.toJSONString(child));
                child.setChildren(temp);

                Map<String, Object> map = new HashMap<>();
                map.put("params", data);
                categoryMapper.insertCategory(map);
                int id = Integer.parseInt(map.get("id").toString());
                child.setId(id);

                que.add(child);
            }
        }
        return getCategoryRecursively(pid);
    }

    @Override
    public int deleteCategory(int id) {
        return categoryMapper.deleteCategory(id);
    }

    @Override
    public int deleteCategory(String name) {
        return categoryMapper.deleteCategoryByName(name);
    }

    @Override
    @Transactional
    public int deleteCategoryRecursively(int id) {
        Category root = getCategory(id);
        // had sorted at sql query
        List<Category> descendants = categoryMapper.getDescendantCategories(root.relPath());
        // need delete from child to parent
        Collections.reverse(descendants);
        // delete root at last
        descendants.add(root);

        for (Category descendant : descendants) {
            deleteCategory(descendant.getId());
        }

        return descendants.size();
    }

    @Override
    @Transactional
    public int deleteCategoryRecursively(String name) {
        Category root = getCategory(name);
        return deleteCategoryRecursively(root.getId());
    }
}
