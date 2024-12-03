package com.example.blog.config;

import com.example.blog.entity.UserInfo;
import com.example.blog.reporsitry.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
// Service class to fetch user details and integrate with Spring Security's authentication process
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository; // Repository to interact with the database for UserInfo entities

    @Override
    // Loads a user's details based on their email (used as the username)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user information from the database using the email
        Optional<UserInfo> userInfo = repository.findByEmail(email);

        // If the user is found, map it to UserInfoUserDetails; otherwise, throw an exception
        return userInfo.map(UserInfoUserDetails::new) // Convert UserInfo to UserInfoUserDetails
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email)); // Handle missing user
    }
}
