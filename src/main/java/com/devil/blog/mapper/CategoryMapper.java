package com.devil.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Category;

@Mapper
@Repository
public interface CategoryMapper {
    Category getCategory(int id);
    List<Category> getAllCategories();
    List<Category> getDescendantCategories(String path);

    boolean updateCategory(int id, Map<String, Object> map);
    int insertCategory(Map<String, Object> params);
    int deleteCategory(int id);
}
