package ru.ecom4u.web.springconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import ru.ecom4u.web.security.DBAuthenticationProvider;
import ru.ecom4u.web.security.UserDetailsServiceImpl;

/**
 * Created by Evgeny on 01.05.14.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DBAuthenticationProvider dbAuthenticationProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailServiceImpl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(dbAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                .authorizeRequests()
                .anyRequest().hasAuthority("GUEST");*/



        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/private/**").hasAnyRole("USER", "MANAGER", "ADMIN");

        // http.csrf().disable();
        http
                .formLogin().loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/?login")
                .failureUrl("/login")
                .permitAll()

                .and()
                .logout()
                .logoutSuccessUrl("/?logout")

        .and().rememberMe()
                .key("your_key")
                .rememberMeServices(rememberMeServices());
        http.rememberMe();

        // http.logout().logoutSuccessUrl("/");
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        // Key must be equal to rememberMe().key()
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("your_key", userDetailServiceImpl);
        rememberMeServices.setCookieName("remember_me_cookie");
        rememberMeServices.setParameter("remember_me_checkbox");
        rememberMeServices.setTokenValiditySeconds(2678400); // 1month
        return rememberMeServices;
    }

}
