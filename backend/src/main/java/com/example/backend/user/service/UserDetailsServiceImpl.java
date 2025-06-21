package com.example.backend.user.service;

import com.example.backend.user.model.MyUser;
import com.example.backend.user.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MyUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MyUser> user= userRepository.findByEmail(email);
        if(user.isPresent()){
            return buildUser(user.get().getEmail(),user.get().getPassword(),"USER");
        }
        throw new UsernameNotFoundException(email);

    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return buildUser(user.get().getEmail(), user.get().getPassword(), "USER");
        }
        throw new UsernameNotFoundException("User not found with id: " + id);
    }

    public UserDetails buildUser(String email,String password,String role){
        return User.builder()
                .username(email)
                .password(password)
                .roles(role)
                .build();
    }
}
