package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.devil.blog.entity.Tag;

@Mapper
@Repository
public interface TagMapper {
    public List<Tag> queryTags();
}
