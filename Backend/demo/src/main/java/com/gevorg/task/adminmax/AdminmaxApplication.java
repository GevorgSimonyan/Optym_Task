package com.gevorg.task.adminmax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties")
@EnableSwagger2
@Configuration
@AutoConfigureAfter(MongoAutoConfiguration.class)
@AutoConfigureBefore(MongoDataAutoConfiguration.class)
public class AdminmaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminmaxApplication.class, args);
    }
}
