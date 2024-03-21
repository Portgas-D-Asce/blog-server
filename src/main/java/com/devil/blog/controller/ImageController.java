package com.devil.blog.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

import com.devil.blog.entity.Error;
import com.devil.blog.entity.Image;
import com.devil.blog.service.ImageService;
import com.devil.blog.service.ImageServiceImpl;

import net.coobird.thumbnailator.Thumbnails;

@CrossOrigin
@RestController
public class ImageController {
    @Autowired
    private ImageService imageService = new ImageServiceImpl();

    /*
    * where is post + put + delete ?
    * 不单独提供这些接口, 统一从 article 起手
    * */

    @GetMapping("/api/v1/images")
    public ResponseEntity<Object> getContent(@RequestParam String name,
                                             @RequestParam(required = false) Integer ratio) throws IOException {
        Image image = imageService.getImage(name);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);

        if(ratio != null && ratio >= 2) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getContent());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            Thumbnails.of(byteArrayInputStream).scale(1.0 / ratio).toOutputStream(byteArrayOutputStream);
            image.setContent(byteArrayOutputStream.toByteArray());
        }

        return new ResponseEntity<>(image.getContent(), httpHeaders, HttpStatus.OK);
    }
}
