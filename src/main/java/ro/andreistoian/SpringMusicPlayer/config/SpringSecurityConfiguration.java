package ro.andreistoian.SpringMusicPlayer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ro.andreistoian.SpringMusicPlayer.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    AuthFailureHandler authFailureHandler;

    @Autowired
    AuthSuccessHandler authSuccessHandler;

    @Autowired
    LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("andrei").password(getEncoder().encode("constanta1987")).
                roles("USER").and().passwordEncoder(getEncoder());
    }

     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**","/public/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("u_name")
                .passwordParameter("lgi_password")
                .failureHandler(authFailureHandler)
                .successHandler(authSuccessHandler)
                .permitAll()
                .and()
                .logout()

                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

           //     .logoutSuccessHandler(logoutSuccessHandler)
                .logoutSuccessUrl("/")
                .deleteCookies("remember-me", "JSESSIONID")
                .permitAll()
                .and().csrf().disable()
                .cors().disable()
                .rememberMe();
    }



}
