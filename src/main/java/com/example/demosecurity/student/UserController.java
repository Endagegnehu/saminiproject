package com.example.demosecurity.student;

import com.example.demosecurity.dto.UserRegistrationRequest;
import com.example.demosecurity.dto.UserRegistrationResponse;
import com.example.demosecurity.service.MyUserDetailsService;
import com.example.demosecurity.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping("/")
    public String getStudent(){
        return ("<h1>Hello World</h1>");
    }

    @GetMapping("/user")
    public String user(){
        return ("<h1> User</h1>");
    }

    @GetMapping("/admin")
    public String admin(){
        return ("<h1> Admin</h1>");
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody UserRegistrationRequest registrationRequest) throws Exception {
           try {
               authManager.authenticate(new UsernamePasswordAuthenticationToken(
                       registrationRequest.getUserName(),
                       registrationRequest.getPassword()
               ));
           }catch (BadCredentialsException badCredentialsException){
               throw new Exception("Incorrect user Name or password",badCredentialsException);
           }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(registrationRequest.getUserName());
           final String jwt  = jwtTokenUtil.generateToken(userDetails);
            logger.info("JWT: " + jwt);
           return ResponseEntity.ok(new UserRegistrationResponse(jwt));
    }
}
