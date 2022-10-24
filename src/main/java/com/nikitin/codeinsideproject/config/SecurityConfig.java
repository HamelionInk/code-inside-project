package com.nikitin.codeinsideproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/person*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/person/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/notes/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/notes*").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.PATCH,"/person/**", "/notes/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/person*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/notes/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/swagger-ui/index.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/perform_login")
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
