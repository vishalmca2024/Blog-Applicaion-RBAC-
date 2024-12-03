package com.example.blog;

import com.example.blog.entity.UserInfo;
import com.example.blog.reporsitry.UserInfoRepository;
import com.example.blog.service.ProductService;
import com.example.blog.service.ServiceInterface.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @Mock
    private UserInfoRepository userRepository;

    @InjectMocks
    private ProductService userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = userServiceImpl;
    }

    @Test
    void testAddUser() {
        UserInfo user = new UserInfo();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.save(any(UserInfo.class))).thenReturn(user);

        String result = userService.addUser(user);

        assertEquals("user added to system", result);

        verify(userRepository, times(1)).save(any(UserInfo.class));
    }
}
