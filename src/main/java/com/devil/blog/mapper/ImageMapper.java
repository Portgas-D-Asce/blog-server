package com.devil.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Image;

@Mapper
@Repository
public interface ImageMapper {
    public Image getImage(int id);

    public int insertImage(Image image);

    public boolean deleteImage(int id);
}
