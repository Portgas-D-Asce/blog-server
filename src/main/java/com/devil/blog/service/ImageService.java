package com.devil.blog.service;

//import java.util.List;
//import java.util.Map;

import com.devil.blog.entity.Image;

public interface ImageService {
    Image getImage(int id);
    Image getImage(String name);

    //List<Image> getAllImages();

    //Image insertImage(Map<String, Object> params);

    int deleteImage(int id);
    int deleteImage(String name);
}
