package com.example.demo.User.Security;

import com.example.demo.User.Jwt.JwtEntityPoint;
import com.example.demo.User.Jwt.JwtTokenFIlter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter{


	
	@Autowired
	UserDetailsService userDetailsService;
	
    @Autowired
    JwtEntityPoint jwtEntryPoint;

    @Bean
    public JwtTokenFIlter jwtTokenFIlter(){
        return new JwtTokenFIlter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService).passwordEncoder((passwordEncoder()));
    }

    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // TODO Auto-generated method stub
        return super.authenticationManagerBean();
    }

    

    
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        // TODO Auto-generated method stub
        return super.authenticationManager();
    }

    


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/auth/**", "/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        
        http.cors().and().csrf(AbstractHttpConfigurer::disable)
        .headers(httpSecurityHeadersConfigurer -> {
            httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        });
             
        http.addFilterBefore(jwtTokenFIlter(), UsernamePasswordAuthenticationFilter.class);
    }


    

    
}
