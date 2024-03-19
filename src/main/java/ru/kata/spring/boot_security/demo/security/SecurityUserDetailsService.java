package ru.kata.spring.boot_security.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        SecurityUserDetails securityUserDetails = new SecurityUserDetails(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found exception");
        }
        return new org.springframework.security.core.userdetails.User(securityUserDetails.getUsername(),
                securityUserDetails.getPassword(),
                securityUserDetails.getAuthorities());
    }
}