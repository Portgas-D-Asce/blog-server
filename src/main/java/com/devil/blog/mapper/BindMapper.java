package com.devil.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BindMapper {
    public int bindTags(int aid, List<Integer> tids);
    public int unbindTags(int aid);

    public int bindArticles(int tid, List<Integer> aids);
    public int unbindArticles(int tid);
}
