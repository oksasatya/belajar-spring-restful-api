package restful.api.springboot.belajarspringrestfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.RegisterUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UpdateUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UserResponse;
import restful.api.springboot.belajarspringrestfulapi.repository.UserRepository;
import restful.api.springboot.belajarspringrestfulapi.security.BCrypt;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterUserRequest request){
        validationService.validate(request);
        if(userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username Already Registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setName(request.getUsername());

        userRepository.save(user);
    }

    public UserResponse get(User user){
        return UserResponse.builder().username(user.getUsername()).name(user.getName()).build();
    }


    @Transactional
    public UserResponse update(User user, UpdateUserRequest request){
        validationService.validate(request);
        if (Objects.nonNull(request.getName())) {
            user.setName(request.getName());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);

        return UserResponse.builder().username(user.getUsername()).name(user.getName()).build();
    }



}
