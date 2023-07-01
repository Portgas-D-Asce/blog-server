package com.devil.blog.service;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Image;
import com.devil.blog.mapper.ImageMapper;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    
    //@Override
    //public Image getImage(int id) {
    //    return imageMapper.getImage(id);
    //}

    @Override
    public Image getImageByName(String name) {
        return imageMapper.getImageByName(name);
    }

    //@Override
    //public List<Image> getAllImages() {
    //    return imageMapper.getAllImages();
    //}

    //@Override
    //public Image insertImage(Map<String, Object> params) {
    //    Map<String, Object> map = new HashMap<>();
    //    map.put("params", params);

    //    imageMapper.insertImage(map);
    //    int id = Integer.parseInt(map.get("id").toString());

    //    Image image = imageMapper.getImage(id);
    //    
    //    return image;
    //}

    //@Override
    //public int deleteImage(int id) {
    //    return imageMapper.deleteImage(id);
    //}
}
