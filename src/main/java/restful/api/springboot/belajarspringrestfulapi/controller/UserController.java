package restful.api.springboot.belajarspringrestfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.RegisterUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UpdateUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UserResponse;
import restful.api.springboot.belajarspringrestfulapi.model.WebResponse;
import restful.api.springboot.belajarspringrestfulapi.service.UserService;

import java.awt.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user){
        UserResponse userResponse = userService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }


    @PatchMapping(
            path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(User user,@RequestBody UpdateUserRequest updateUserRequest){
        UserResponse userResponse = userService.update(user, updateUserRequest);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
}
