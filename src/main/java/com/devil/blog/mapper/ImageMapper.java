package com.devil.blog.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Image;

@Mapper
@Repository
public interface ImageMapper {
    public Image getImage(int id);

    public int insertImage(Map<String, Object> map);

    public int deleteImage(int id);
}
