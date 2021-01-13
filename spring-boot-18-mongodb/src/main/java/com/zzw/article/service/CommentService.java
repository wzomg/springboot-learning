package com.zzw.article.service;

import com.zzw.article.dao.CommentDao;
import com.zzw.article.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

//评论的业务层
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    //注入MongoTemplate
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 保存一个评论
     */
    public void saveComment(Comment comment) {
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        commentDao.save(comment);
    }

    /**
     * 更新评论
     */
    public void updateComment(Comment comment) {
        commentDao.save(comment);
    }

    /**
     * 根据id删除评论
     */
    public void deleteCommentById(String id) {
        commentDao.deleteById(id);
    }

    /**
     * 查询所有评论
     */
    public List<Comment> findCommentList() {
        return commentDao.findAll();
    }

    /**
     * 根据id查询评论
     */
    public Comment findCommentById(String id) {
        return commentDao.findById(id).get();
    }

    /**
     * 根据父id查询分页列表
     */
    public Page<Comment> findCommentListPageByParentid(String parentid, int page, int size) {
        return commentDao.findByParentid(parentid, PageRequest.of(page - 1, size));
    }

    /**
     * 点赞-以下操作效率低
     */
    public void updateCommentThumbupToIncrementingOld(String id) {
        Comment comment = commentDao.findById(id).get();
        comment.setLikenum(comment.getLikenum() + 1);
        commentDao.save(comment);
    }

    /**
     * 点赞数+1
     */
    public void updateCommentLikenum(String id) {
        //查询对象
        Query query = Query.query(Criteria.where("_id").is(id));
        //更新对象
        Update update = new Update();
        //局部更新，相当于$set
        //update.set(key,value)
        //递增$inc
        //update.inc("likenum",1);
        update.inc("likenum");
        //参数1：查询对象
        //参数2：更新对象
        //参数3：集合名或实体类的类型Comment.class
        mongoTemplate.updateFirst(query, update, "comment");
    }
}
