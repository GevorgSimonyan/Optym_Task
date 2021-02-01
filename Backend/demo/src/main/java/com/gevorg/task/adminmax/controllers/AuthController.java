package com.gevorg.task.adminmax.controllers;

import com.gevorg.task.adminmax.config.JwtTokenProvider;
import com.gevorg.task.adminmax.models.AuthBody;
import com.gevorg.task.adminmax.models.RegistratedUser;
import com.gevorg.task.adminmax.models.User;
import com.gevorg.task.adminmax.models.UserRole;
import com.gevorg.task.adminmax.utilitis.Util;
import com.gevorg.task.adminmax.repositories.UserRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@Api(value = "/api/auth", tags = {"Api Auth"}, description = "Authentication", produces = "application/json")
public class AuthController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) throws Exception {

        try {
            if (data == null || data.getEmail() == null || data.getEmail().isEmpty()
                    || data.getPassword() == null || data.getPassword().isEmpty()) {
                throw new Exception("invalid email or password");
            }

            User user = this.users.findByEmail(data.getEmail());


            if (user == null) {

                User newUser = new User();
                newUser.setEmail(data.getEmail());
                newUser.setRole(UserRole.USER.toString());
                newUser.setPassword(data.getPassword());


                String token = jwtTokenProvider.createToken(newUser.getEmail(), newUser.getRole());
                newUser.setToken(token);
                userService.saveUser(newUser);

//                Map<Object, Object> model = userService.getReturnedUser(newUser);
                Map<Object, Object> model = new HashMap<>();
//                model.put("user", newUser);
                model.put("token", token);
                userService.logUserLogin(newUser);
                return OK(model, "User registered successfully");

            } else {

                String username = data.getEmail();
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
                String token = jwtTokenProvider.createToken(username, user.getRole());
                user.setToken(token);
                Map<Object, Object> model = new HashMap<>();
//                model.put("user", user);
                model.put("token", token);
                userService.saveWhithoutPasswd(user);
                userService.logUserLogin(user);
                return OK(model);
            }

        } catch (Exception e) {
            throw new Exception("wrong credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout() throws Exception {
        try {
            User user = getCurrentUser();
            user.setToken("");
            userService.saveWhithoutPasswd(user);
            userService.logUserLogOut(user);
            return OK( "User successfully logged out");
        } catch (Exception e) {
            throw new Exception("Invalid token");
        }
    }
}
