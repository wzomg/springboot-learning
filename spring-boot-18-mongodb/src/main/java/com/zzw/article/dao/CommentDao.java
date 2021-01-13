package com.zzw.article.dao;

import com.zzw.article.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<Comment, String> {
    //根据父id，查询子评论的分页列表，注意命名规范
    Page<Comment> findByParentid(String parentid, Pageable pageable);
}
