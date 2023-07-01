package com.devil.blog.controller;

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

@CrossOrigin
@RestController
public class ImageController {
    @Autowired
    private ImageService imageService = new ImageServiceImpl();

    //@GetMapping("/api/v1/images/{id}")
    //public ResponseEntity<Object> getContent(@PathVariable("id") Integer id) throws IOException {
    //    Image image = imageService.getImage(id);
    //    HttpHeaders httpHeaders = new HttpHeaders();
    //    httpHeaders.setContentType(MediaType.IMAGE_JPEG);

    //    return new ResponseEntity<Object>(image.getContent(), httpHeaders, HttpStatus.OK);
    //}

    @GetMapping("/api/v1/images")
    public ResponseEntity<Object> getContent(@RequestParam(required = false) String name) throws IOException {
        if(name == null) {
            Error error = new Error(404, "name is required.", 
                "only support query by name currently, until someone need more.");
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
        Image image = imageService.getImageByName(name);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<Object>(image.getContent(), httpHeaders, HttpStatus.OK);
    }

    //@PostMapping("/api/v1/images")
    //public ResponseEntity<Object> insertImage(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
    //    Map<String, Object> map = new HashMap<String, Object>();
    //    if(multipartFile != null) {
    //        map.put("content", multipartFile.getBytes());
    //        String name = multipartFile.getOriginalFilename();
    //        if(name != null) {
    //            map.put("name", name);
    //        }
    //    }
    //    Image image = imageService.insertImage(map);
    //    return new ResponseEntity<>(image, HttpStatus.OK);
    //}

    //@DeleteMapping("/api/v1/images/{id}")
    //public ResponseEntity<Object> deleteImage(@PathVariable("id") Integer id) {
    //    imageService.deleteImage(id);
    //    return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    //}
}
