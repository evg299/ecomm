package ru.ecom4u.web.springconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.ecom4u.web.security.DBAuthenticationProvider;

/**
 * Created by Evgeny on 01.05.14.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DBAuthenticationProvider dbAuthenticationProvider;

    /*
    @Override
    public void init(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources*//**");
    }
    */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(dbAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .antMatchers("/protected*//**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/confidential*//**").access("hasRole('ROLE_SUPERADMIN')")
                .anyRequest().anonymous();*/

        /*http.authorizeRequests()
                .antMatchers("/signup", "/about").permitAll()
                .antMatchers("/admin*//**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin().loginPage("/login").permitAll();*/

        http.rememberMe();

        http.authorizeRequests()
                .antMatchers("/app/**").hasRole("ADMIN");

        http.formLogin()
                .loginPage("/index.jsp")
                .defaultSuccessUrl("/app/")
                .failureUrl("/index.jsp")
                .permitAll();

        http.logout()
                .logoutSuccessUrl("/");

        /*http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable().formLogin().permitAll();
        http.logout().permitAll();*/
    }
}
