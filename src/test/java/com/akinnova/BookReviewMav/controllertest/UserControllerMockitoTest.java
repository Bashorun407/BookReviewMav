package com.akinnova.BookReviewMav.controllertest;

import com.akinnova.BookReviewMav.controller.UserController;
import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.service.userservice.UserServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {UserControllerMockitoTest.class})
public class UserControllerMockitoTest {

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    List<UserEntity> userEntityList;

    UserEntity user;


}
