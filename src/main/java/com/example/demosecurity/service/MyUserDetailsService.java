package com.example.demosecurity.service;

import com.example.demosecurity.domain.User;
import com.example.demosecurity.domain.UserRepository;
import com.example.demosecurity.user.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(user_name);
        user.orElseThrow(()-> new UsernameNotFoundException("No user found: "+ user_name));
        return user.map(MyUserDetails::new).get();
    }
}
