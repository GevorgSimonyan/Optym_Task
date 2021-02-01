package com.gevorg.task.adminmax.migrations;

import com.gevorg.task.adminmax.models.User;
import com.gevorg.task.adminmax.models.UserRole;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ChangeLog
public class Changelogs {


    @ChangeSet(order = "001", id = "change", author = "Gevorg")
    public void createAdminUser(MongoTemplate mongoTemplate) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminsky");
        user.setEmail("admin@admin.com");
        user.setRole(UserRole.ADMIN.toString());
        user.setPassword(encoder.encode("123456"));
        mongoTemplate.save(user);
    }

}
