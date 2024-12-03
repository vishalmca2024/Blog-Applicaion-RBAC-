package com.example.blog.reporsitry;

import com.example.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//Comment repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);

}
