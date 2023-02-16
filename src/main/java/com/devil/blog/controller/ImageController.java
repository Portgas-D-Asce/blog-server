package com.devil.blog.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devil.blog.entity.Image;
import com.devil.blog.service.ImageService;
import com.devil.blog.service.ImageServiceImpl;

@CrossOrigin
@RestController
public class ImageController {
    @Autowired
    private ImageService imageService = new ImageServiceImpl();

    @GetMapping("/image/{id}")
    public void getContent(@PathVariable("id") int id, HttpServletResponse response) throws IOException {
        Image image = imageService.getImage(id);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(image.getType());
        response.getOutputStream().write(image.getContent());
    }

    @PostMapping("/image")
    public int insertImage(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String name = multipartFile.getOriginalFilename();
        if(name != null) {
            name = name.substring(0, name.lastIndexOf("."));
        }
        String type = multipartFile.getContentType();
        byte[] bytes = multipartFile.getBytes();
        Image image = new Image(0, name, type, bytes);
        return imageService.insertImage(image);
    }

    @DeleteMapping("/image/{id}")
    public boolean deleteImage(@PathVariable("id") int id) {
        return imageService.deleteImage(id);
    }
}
