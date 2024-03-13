package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.kata.spring.boot_security.demo.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return  authenticationProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //регистрация первоначальных пользователей
    //username - admin, pass - admin
    //username - user, pass - user
    /*insert into kata.users values('1', '$2a$12$N1aahc85LpRCpgM4xPmNROjKXsPXeaKTMWnhJloUi3nhmdEsu4Cjq', 'admin');
    insert into kata.users values('2', '$2a$12$pIoktK5UxIxlpuOD11tPzuX1TPRWZt/C5HTZOBlsTLfnSHsDhRlNK', 'user');
    insert into kata.users values('3', '$2a$12$pIoktK5UxIxlpuOD11tPzuX1TPRWZt/C5HTZOBlsTLfnSHsDhRlNK', 'user2');
    insert into kata.users values('4', '$2a$12$pIoktK5UxIxlpuOD11tPzuX1TPRWZt/C5HTZOBlsTLfnSHsDhRlNK', 'user3');

    insert into kata.roles values('1', 'ROLE_ADMIN');
    insert into kata.roles values('2', 'ROLE_USER');

    insert into kata.users_roles values('1', '1');
    insert into kata.users_roles values('2', '2');
    insert into kata.users_roles values('3', '2');
    insert into kata.users_roles values('4', '2');*/
}