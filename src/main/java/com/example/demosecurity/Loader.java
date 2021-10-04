package com.example.demosecurity;

import com.example.demosecurity.domain.User;
import com.example.demosecurity.service.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Loader implements CommandLineRunner {

    @Autowired
    RegistrationServiceImpl registrationService;

    @Override
    public void run(String... args) throws Exception {
        createAdmin();
    }
    public void createAdmin(){
        User user = new User();
            user.setUserName("admin");
            user.setPassword("admin");
            user.setRoles("ROLE_ADMIN");
            user.setActive(true);
        registrationService.addUser(user);
    }
}
