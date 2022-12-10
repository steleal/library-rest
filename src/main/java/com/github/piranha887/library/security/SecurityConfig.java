package com.github.piranha887.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@EnableWebSecurity
@Component
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/", "/index").permitAll()
                .and()
                .authorizeRequests().antMatchers("/authors", "/books", "/genres", "/publishers").authenticated()
                .and()
                .authorizeRequests().antMatchers("/authors/new", "/authors/edit", "/authors/save", "/authors/delete")
                .hasRole("EDITOR")
                .and()
                .authorizeRequests().antMatchers("/genres/new", "/genres/edit", "/genres/save", "/genres/delete")
                .hasRole("EDITOR")
                .and()
                .authorizeRequests().antMatchers("/publishers/new", "/publishers/edit", "/publishers/save", "/publishers/delete")
                .hasRole("EDITOR")
                .and()
                .authorizeRequests().antMatchers("/books/new", "/books/edit", "/books/save", "/books/delete")
                .hasRole("EDITOR")
                .and()
                .authorizeRequests().antMatchers("/users/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT USERNAME,PASSWORD,ENABLED FROM USERS WHERE USERNAME = ?")
                .authoritiesByUsernameQuery("SELECT USERNAME, ROLE FROM USERS WHERE USERNAME = ?");
    }

}
