package com.gevorg.task.adminmax.controllers;

import com.gevorg.task.adminmax.config.JwtTokenProvider;
import com.gevorg.task.adminmax.models.*;
import com.gevorg.task.adminmax.repositories.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/user")
@Api(value = "/api/user", tags = {"Api User"}, description = "Account management", produces = "application/json")
public class UserController extends BaseController {


    @Autowired
    UserRepository users;

    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordModel data) throws Exception {
        try {
            User currentUser = getCurrentUser();

            if (userService.checkPassword(currentUser.getPassword(), data.getOldPassword())) {
                currentUser.setPassword(data.getNewPassword());
                userService.saveUserPassword(currentUser);
                return OK("password successfully updated");
            } else {
                return FAULT("wrong password", HttpStatus.BAD_REQUEST.value());
            }

        } catch (Exception e) {
            throw new Exception("Invalid token");
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody EditUserModel data) throws Exception {
        try {
            User currentUser = getCurrentUser();

            if (data.getFirstName() != null) {
                currentUser.setFirstName(data.getFirstName());
            }

            if (data.getLastName() != null) {
                currentUser.setLastName(data.getLastName());
            }

            if (data.getNewPassword() != null && !data.getNewPassword().isEmpty()) {

                if (data.getOldPassword() != null && userService.checkPassword(currentUser.getPassword(), data.getOldPassword())) {
                    currentUser.setPassword(data.getNewPassword());
                    userService.saveUserPassword(currentUser);
                } else {
                    return FAULT("wrong password", HttpStatus.BAD_REQUEST.value());
                }

            } else {

                userService.saveWhithoutPasswd(currentUser);
            }

            return OK(currentUser, "User successfully updated");
        } catch (Exception e) {
            throw new Exception("Invalid token");
        }
    }


//    @PostMapping("/update")
//    public ResponseEntity updateUser(@RequestBody User user) throws Exception {
//        try {
//            User currentUser = getCurrentUser();
//            currentUser.setFirstName(user.getFirstName());
//            currentUser.setLastName(user.getLastName());
//            userService.saveWhithoutPasswd(currentUser);
//            return OK(currentUser, "User successfully updated");
//        } catch (Exception e) {
//            throw new Exception("Invalid token");
//        }
//    }

    @GetMapping("/getUser")
    public ResponseEntity<User> getUser() throws Exception {
        try {
            User currentUser = getCurrentUser();
            return OK(currentUser);
        } catch (Exception e) {
            throw new Exception("Invalid token");
        }
    }
}
