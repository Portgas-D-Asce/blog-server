package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Category;

@Mapper
@Repository
public interface CategoryMapper {
    public Category getCategory(int id);
    public List<Category> getAllCategories();

    public boolean updateCategory(int id, Map<String, Object> map);
    public int insertCategory(Map<String, Object> params);
    public int deleteCategory(int id);
}
