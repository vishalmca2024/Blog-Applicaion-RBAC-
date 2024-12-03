package com.example.blog.reporsitry;

import com.example.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
//PostRepo
public interface PostRepository extends JpaRepository<Post, Long> {

}
