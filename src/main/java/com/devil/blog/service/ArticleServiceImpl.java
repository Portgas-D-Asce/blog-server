package com.devil.blog.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devil.blog.entity.Article;
import com.devil.blog.mapper.ArticleMapper;
import com.devil.blog.mapper.BindMapper;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BindMapper bindMapper;

    @Override
    public Article getArticle(int id) {
        Article article = articleMapper.getArticle(id);
        return article;
    }

    @Override
    public String getContent(int id) {
        Article article = articleMapper.getArticle(id);
        String name = article.getName();
        File file = new File("/Users/pk/Note/" + name + "/index.md");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temp = "";
            while((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp + "\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
        
        return stringBuilder.toString();
    }

    @Override
    @Transactional
    public int insertArticle(Article article, List<Integer> tids) {
        articleMapper.insertArticle(article);
        int aid = article.getId();
        if(tids != null && tids.size() > 0) {
            bindMapper.bindTags(aid, tids);
        }
        return aid;
    }

    @Override
    @Transactional
    public boolean deleteArticle(int id) {
        bindMapper.unbindTags(id);

        boolean res = articleMapper.deleteArticle(id);
        return res;
    }
}
