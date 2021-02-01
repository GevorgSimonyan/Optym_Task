package com.gevorg.task.adminmax.utilitis;

import com.gevorg.task.adminmax.models.Log;
import com.gevorg.task.adminmax.models.User;
import com.gevorg.task.adminmax.services.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static boolean checkUserExistence(UserService userService, User user) throws Exception {
        User existingUser = userService.findUserByEmail(user.getEmail());
        return existingUser != null;
    }

    public static boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }

    public static Pageable getPagging(Integer pageNo, Integer pageSize, String sortBy, Integer direction){

        Sort.Direction sortDirection = Sort.DEFAULT_DIRECTION;
        if (direction == 1) {
            sortDirection = Sort.Direction.ASC;
        }
        if (direction == -1) {
            sortDirection = Sort.Direction.DESC;
        }
        return new PageRequest(pageNo, pageSize, sortDirection, sortBy);

    }


}
