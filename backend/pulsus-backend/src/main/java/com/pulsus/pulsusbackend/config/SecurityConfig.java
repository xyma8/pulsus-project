package com.pulsus.pulsusbackend.config;

import com.pulsus.pulsusbackend.exception.JwtAuthenticationEntryPoint;
import com.pulsus.pulsusbackend.filter.JwtAuthFilter;
import com.pulsus.pulsusbackend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private JwtAuthFilter authFilter;


    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthFilter authFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.authFilter = authFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(request ->
                                new CorsConfiguration().applyPermitDefaultValues())

                )
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);

        return http.build();
    }
    */

    @Bean
    public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/users/signup", "/api/auth/generateToken", "/api/auth/checkToken").permitAll()
                        .requestMatchers("/api/users/**").authenticated()
                        .requestMatchers("/api/admin/**").authenticated()
                        .requestMatchers("/api/workouts/**").authenticated()
                        .requestMatchers("/api/posts/**").authenticated()
                        .requestMatchers("/api/subscriptions/**").authenticated()
                        .requestMatchers("/api/workoutsLikes/**").authenticated()
                        .requestMatchers("/api/statistics/**").authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    /*

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests().requestMatchers("/api/users/signup", "/api/auth/generateToken").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/users/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/admin/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/workouts/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/posts/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/subscriptions/**").authenticated()
                .and()
                .authorizeHttpRequests().requestMatchers("/api/workoutsLikes/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
