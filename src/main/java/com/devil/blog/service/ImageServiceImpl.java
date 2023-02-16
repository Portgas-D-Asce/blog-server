package com.devil.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devil.blog.entity.Image;
import com.devil.blog.mapper.ImageMapper;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    
    @Override
    public Image getImage(int id) {
        return imageMapper.getImage(id);
    }

    @Override
    public int insertImage(Image image) {
        System.out.println(image.toString());
        imageMapper.insertImage(image);
        return image.getId();
    }

    @Override
    public boolean deleteImage(int id) {
        return imageMapper.deleteImage(id);
    }
}
