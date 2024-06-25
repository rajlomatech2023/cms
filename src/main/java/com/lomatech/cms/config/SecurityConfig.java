package com.lomatech.cms.config;

import com.lomatech.cms.authentication.AuthenticationFilter;
import com.lomatech.cms.authentication.CustomUserDetailsService;
import com.lomatech.cms.user.UserService;
import com.lomatech.cms.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                //.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().authenticated())
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                .requestMatchers("/api/admin")
                .hasRole("admin")
                .requestMatchers("/api/user")
                .hasRole("user")
                .requestMatchers("/api/public")
                .permitAll()
                .anyRequest()
                .authenticated())
                .addFilter(getAuthenticationFilter())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                //.formLogin(AbstractAuthenticationFilterConfigurer::permitAll);

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder){
        /*UserDetails userDetailsService = User.withUsername("cmsuser")
                .password(passwordEncoder().encode("cmsuser"))
                .roles("user").build();

        UserDetails adminUserDetails = User.withUsername("cmsadmin")
                .password(passwordEncoder().encode("cmsadmin"))
                .roles("admin").build();

        return new InMemoryUserDetailsManager(userDetailsService, adminUserDetails);*/
        return new CustomUserDetailsService(bCryptPasswordEncoder);

    }

    @Bean
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setFilterProcessesUrl("/login");

        return authenticationFilter;
    }
}
