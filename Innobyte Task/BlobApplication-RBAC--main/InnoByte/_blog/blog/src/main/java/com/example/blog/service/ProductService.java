package com.example.blog.service;

import com.example.blog.entity.UserInfo;
import com.example.blog.reporsitry.UserInfoRepository;
import com.example.blog.service.ServiceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marks the class as a service bean for Spring's component scan
public class ProductService implements UserService {

    @Autowired
    private UserInfoRepository repository; // Repository to interact with the UserInfo entity in the database

    @Autowired
    private PasswordEncoder passwordEncoder; // Encoder for encrypting passwords before storing them in the database

    /**
     * Adds a new user to the system.
     *
     * @param userInfo The user information to be added
     * @return A confirmation message indicating the user was added
     */
    @Override
    public String addUser(UserInfo userInfo) {
        // Encrypt the user's password before storing it in the database
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        // Save the user information to the repository (database)
        repository.save(userInfo);

        // Return a confirmation message
        return "user added to system";
    }
}
