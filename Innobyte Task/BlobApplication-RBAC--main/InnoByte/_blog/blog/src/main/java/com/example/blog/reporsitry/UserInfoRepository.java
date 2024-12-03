package com.example.blog.reporsitry;

import com.example.blog.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//UserRepo
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    //Optional<UserInfo> findByName(String username);
    Optional<UserInfo> findByEmail(String email);
    UserInfo findByUsername(String username);

}
