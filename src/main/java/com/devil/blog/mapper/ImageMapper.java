package com.devil.blog.mapper;

//import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Image;

@Mapper
@Repository
public interface ImageMapper {
    Image getImage(int id);

    Image getImageByName(String name);

    //List<Image> getAllImages();

    int insertImage(Map<String, Object> map);

    //int deleteImage(int id);
    int deleteImagesByArticleId(String id);
}
