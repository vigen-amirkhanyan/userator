package com.example.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.sql.Timestamp;

@Controller
@RequestMapping(path = "/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private long EXPIRES_IN_MILLIS = 15 * 60_000;

    @PostMapping(path = "/register")
    public @ResponseBody
    ResponseEntity<?> addNewUser(@RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password,
                    @RequestParam String role) {

        BCryptPasswordEncoder encrypter = new BCryptPasswordEncoder();
        UserEntity n = new UserEntity();
        n.setName(name);
        n.setEmail(email);
        n.setPassword(encrypter.encode(password));
        n.setRole(role);
        userRepository.save(n);
        return new ResponseEntity<String>("User Created", HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public @ResponseBody
    ResponseEntity<?> loginUser(@RequestParam String email,
                                 @RequestParam String password) {

        BCryptPasswordEncoder encrypter = new BCryptPasswordEncoder();
        UserEntity userEntity = userRepository.findUserByEmail(email);
        Boolean userFound = encrypter.matches(password, userEntity.getPassword());

        if (userFound) {
            String hexToken = Integer.toHexString(new SecureRandom().nextInt());
            Timestamp expiresAt = new Timestamp(System.currentTimeMillis() + EXPIRES_IN_MILLIS);
            Token token = new Token(hexToken, expiresAt);
            userEntity.setAccessToken(hexToken);
            userEntity.setAccessTokenExpiresAt(expiresAt);
            userRepository.save(userEntity);

            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/verifyAndExtend")
    public @ResponseBody
    ResponseEntity<?> verifyAndExtendToken(@RequestParam String accessToken) {

        UserEntity userEntity = userRepository.findUserByaccessToken(accessToken);
        if(userEntity==null){
            return new ResponseEntity<String>("Access Token Not Found", HttpStatus.NOT_FOUND);
        }

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Timestamp accessExpirationTimestamp = userEntity.getAccessTokenExpiresAt();

        if (userEntity != null && accessExpirationTimestamp.after(currentTimestamp)) {
            userEntity.setAccessTokenExpiresAt(new Timestamp(System.currentTimeMillis() + EXPIRES_IN_MILLIS));
            userRepository.save(userEntity);
            UserInfo userInfo = new UserInfo(userEntity);
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Expired Access Token, Please login to get new access toekn",
                    HttpStatus.UNAUTHORIZED);
        }
    }

}
