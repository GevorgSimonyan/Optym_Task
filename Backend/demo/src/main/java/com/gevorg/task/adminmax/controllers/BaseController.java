package com.gevorg.task.adminmax.controllers;


import com.gevorg.task.adminmax.models.User;
import com.gevorg.task.adminmax.services.UserService;
import com.gevorg.task.adminmax.utilitis.errors.ApiError;
import com.gevorg.task.adminmax.utilitis.R;
import com.gevorg.task.adminmax.utilitis.types.ServiceUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@ApiResponses({
        @ApiResponse(code = 200, message = R.VALID),
        @ApiResponse(code = 403, message = R.OPERATION_NOT_ALLOWED),
        @ApiResponse(code = 404, message = R.INVALID_URL),
        @ApiResponse(code = 500, message = R.UNEXPECTED_ERROR)
})

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class BaseController {


    @Autowired
    UserService userService;

    protected <T> ResponseEntity OK(T data, String message, int status){
        if(data == null){
            final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "The Object not found");
            return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ServiceUtil.wrapToServiceResult(data, message, status), new HttpHeaders(), HttpStatus.OK);
    }

    protected <T> ResponseEntity OK(T data, String message){
        return OK(data, message, HttpStatus.OK.value());
    }

    protected <T> ResponseEntity OK(T data, int status) {
        return OK(data, "", status);
    }

    protected <T> ResponseEntity OK(String message, int status){
        return new ResponseEntity<>(ServiceUtil.wrapToServiceResult(null, message, status), new HttpHeaders(), HttpStatus.OK);
    }

    protected <T> ResponseEntity OK(String message) {
        return new ResponseEntity<>(ServiceUtil.wrapToServiceResult(null, message, HttpStatus.OK.value()), new HttpHeaders(), HttpStatus.OK);
    }

    protected <T> ResponseEntity OK(T data){
        return OK(data, "", HttpStatus.OK.value());
    }

    protected <T> ResponseEntity OK(){
        return OK(null, "",HttpStatus.OK.value());
    }

    protected <T> ResponseEntity FAULT(String message, int status){
        return new ResponseEntity<>(ServiceUtil.wrapToServiceResult(null, message, status), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    public User getCurrentUser() throws Exception{
        return userService.getLoggedInUser();
    }

}
