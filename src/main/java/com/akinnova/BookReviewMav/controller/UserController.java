package com.akinnova.BookReviewMav.controller;

import com.akinnova.BookReviewMav.dto.userdto.UserCreateDto;
import com.akinnova.BookReviewMav.dto.userdto.UserResponseDto;
import com.akinnova.BookReviewMav.dto.userdto.UserUpdateDto;
import com.akinnova.BookReviewMav.response.ResponsePojo;
import com.akinnova.BookReviewMav.service.userservice.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/auth")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

//    public UserController(UserServiceImpl userService) {
//        this.userService = userService;
//    }

    @PostMapping("/addUser")
    public ResponsePojo<UserResponseDto> addUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.addUser(userCreateDto);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> allUsers(@RequestParam(defaultValue = "1") int pageNum,
                                      @RequestParam(defaultValue = "20" ) int pageSize) {
        return userService.allUsers(pageNum, pageSize);
    }

    @GetMapping("/search")
    public ResponseEntity<?> SearchUser(@RequestParam(required = false) String username,
                                        @RequestParam(required = false) String phoneNumber,
                                        @RequestParam(required = false) String email) {
        return userService.SearchUser(username, phoneNumber, email);
    }

    @GetMapping("/clients")
    public ResponseEntity<?> FindClients(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindClients(pageNum, pageSize);
    }

    @GetMapping("/providers")
    public ResponseEntity<?> FindServiceProviders(@RequestParam(defaultValue = "1") int pageNum,
                                                  @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindServiceProviders(pageNum, pageSize);
    }

    @GetMapping("/admins")
    public ResponseEntity<?> FindAdmins(@RequestParam(defaultValue = "1") int pageNum,
                                        @RequestParam(defaultValue = "20") int pageSize) {
        return userService.FindAdmins(pageNum, pageSize);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
}
