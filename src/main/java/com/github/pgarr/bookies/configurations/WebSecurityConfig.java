package com.github.pgarr.bookies.configurations;

import com.github.pgarr.bookies.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

//    @Autowired
//    private AuthenticationSuccessHandler authenticationSuccessHandler;
//
//    @Autowired
//    private LogoutSuccessHandler logoutSuccessHandler;
//
//    @Autowired
//    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/h2/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/*").permitAll() // TODO: correct permissions
                .and()
                // login
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/books")
                .failureUrl("/login?error=true")
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                // logout
                .logout()
                .logoutUrl("/perform_logout")
                .invalidateHttpSession(false)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/books")
//                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
                .and()
                // exceptions
                .exceptionHandling().accessDeniedPage("/access-denied"); // TODO
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
