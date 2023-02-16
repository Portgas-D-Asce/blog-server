package com.devil.blog.service;

import com.devil.blog.entity.Image;

public interface ImageService {
    public Image getImage(int id);
    public int insertImage(Image image);
    public boolean deleteImage(int id);
}
