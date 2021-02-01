package com.gevorg.task.adminmax.services;

import com.gevorg.task.adminmax.models.Log;
import com.gevorg.task.adminmax.models.User;
import com.gevorg.task.adminmax.repositories.LogRepository;
import com.gevorg.task.adminmax.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MongoTemplate mongoTemplate;


    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    public User findUserByEmail(String email) {
        return repository.findByEmail(email);
    }


    public void saveUser(User user) {
        saveUserPassword(user);
    }

    public boolean checkPassword(String oldPassword, String newPassword) {
        return bCryptPasswordEncoder.matches(newPassword, oldPassword);
    }

    public void saveUserPassword(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void saveWhithoutPasswd(User user) {
        repository.save(user);

    }


    private final static String LOGIN_LOG_TEXT = "user with email %s1 logged in the system at %s2";
    private final static String LOG_OUT_LOG_TEXT = "user with email  %s1 logged out from system at %s2";

    public void logUserLogin(User user) {

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String strDate = dateFormat.format(date);
        String logMessage = String.format(LOGIN_LOG_TEXT, user.getEmail(), strDate);
        Log log = new Log(user, logMessage);
        logRepository.save(log);
    }


    public void logUserLogOut(User user) {

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String strDate = dateFormat.format(date);
        String logMessage = String.format(LOG_OUT_LOG_TEXT, user.getEmail(), strDate);
        Log log = new Log(user, logMessage);
        logRepository.save(log);
    }

//    public Map<Object, Object> getReturnedUser(User user) {
//        Map<Object, Object> model = new HashMap<>();
//        model.put("role", user.getRole());
//        model.put("firstName", user.getFirstName());
//        model.put("lastName", user.getLastName());
//        model.put("email", user.getEmail());
//        model.put("id", user.getId());
//        model.put("token", user.getToken());
//
//        return model;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRole());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    public User getUser(String email) {
        User user = findUserByEmail(email);
        return user;
    }

    private List<GrantedAuthority> getUserAuthority(String role) {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(role));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public User getLoggedInUser() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();
            return getUser(currentUserName);
        }
        throw new Exception("User not found.");
    }


}
