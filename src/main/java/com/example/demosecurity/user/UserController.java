package com.example.demosecurity.user;

import com.example.demosecurity.dto.UserRegistrationRequest;
import com.example.demosecurity.dto.UserRegistrationResponse;
import com.example.demosecurity.service.MyUserDetailsService;
import com.example.demosecurity.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;


    @GetMapping("/admin")
    public void admin(){
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addString("jobId", String.format("period=", java.lang.System.currentTimeMillis()))
                            .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }

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
