package ru.ecom4u.web.springconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    public static final String REMEMBER_ME_KEY = "rmk";
    public static final String REMEMBER_ME_COOKIE = "rmc";
    public static final String REMEMBER_ME_CHECKBOX = "remember_me";

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
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/private/**").hasAnyRole("USER", "MANAGER", "ADMIN");
        http.authorizeRequests().antMatchers("/order/**").hasAnyRole("USER");

        // http.csrf().disable();
        http
                .formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/?login")
                .failureUrl("/login?error")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout")

                .and()
                .rememberMe()
                .key(REMEMBER_ME_KEY)
                .rememberMeServices(rememberMeServices());
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailServiceImpl);
        rememberMeServices.setCookieName(REMEMBER_ME_COOKIE);
        rememberMeServices.setParameter(REMEMBER_ME_CHECKBOX);
        rememberMeServices.setTokenValiditySeconds(2678400); // 1month
        return rememberMeServices;
    }

}
