package com.example.leica_refactoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
       http.authorizeRequests()
//               .antMatchers("/","/login","/create/**", "/delete/**","/find/**","/search/**").permitAll()
//               .antMatchers("/post","/upload").authenticated()
               .anyRequest().permitAll()
               .and()
               .csrf().disable()
               .httpBasic().and()
               .formLogin()
               .defaultSuccessUrl("/");
       return http.build();
    }
}
