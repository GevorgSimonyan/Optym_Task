package com.gevorg.task.adminmax.repositories;

import com.gevorg.task.adminmax.models.Log;
import com.gevorg.task.adminmax.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;


public interface LogRepository extends MongoRepository<Log, Long> {

    @Query(value = "{'user.email' : ?0 }")
    List<Log> findByUserEmail(String email);


}
