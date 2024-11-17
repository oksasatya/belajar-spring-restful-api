package restful.api.springboot.belajarspringrestfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.LoginUserRequest;
import restful.api.springboot.belajarspringrestfulapi.model.TokenResponse;
import restful.api.springboot.belajarspringrestfulapi.repository.UserRepository;
import restful.api.springboot.belajarspringrestfulapi.security.BCrypt;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest request){
        validationService.validate(request);

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username or Password Wrong"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())){
            // success login
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username or Password Wrong");
        }
    }

    private Long next30Days(){
        return System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000;
    }

    @Transactional
    public void logout(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);
        userRepository.save(user);
    }
}
