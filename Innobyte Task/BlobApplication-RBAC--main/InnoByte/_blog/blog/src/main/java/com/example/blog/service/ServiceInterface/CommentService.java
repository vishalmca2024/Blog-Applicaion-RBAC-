package com.example.blog.service.ServiceInterface;

import com.example.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment,Long postid, String username);
    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long id);
    Comment updateComment(Long id, Comment comment, String username);
    void deleteComment(Long id, String username);
}

