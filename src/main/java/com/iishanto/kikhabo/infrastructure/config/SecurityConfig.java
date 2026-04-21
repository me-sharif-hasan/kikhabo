package com.iishanto.kikhabo.infrastructure.config;

import com.iishanto.kikhabo.infrastructure.services.security.ApiKeyAuthFilter;
import com.iishanto.kikhabo.infrastructure.services.security.CorsFilter;
import com.iishanto.kikhabo.infrastructure.services.security.JwtSecurityFilterChain;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@AllArgsConstructor
@Configuration
public class SecurityConfig {
    JwtSecurityFilterChain filterChain;
    CorsFilter corsFilter;
    ApiKeyAuthFilter apiKeyAuthFilter;
    HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->{
                    req.requestMatchers("/api/v1/user/login","/api/v1/user/register","/api/v1/user/social-login","/api/v1/user/verify-otp").permitAll();
                    req.requestMatchers("/api/v1/admin/**").authenticated(); // guarded by ApiKeyAuthFilter
                    req.requestMatchers("/swagger-ui.html").permitAll();
                    req.requestMatchers("/swagger-ui/**").permitAll();
                    req.requestMatchers("/v3/api-docs/**").permitAll();
                    req.requestMatchers("/admin/**").authenticated();
                    req.anyRequest().authenticated();
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filterChain, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                                (request, response, authException) -> handlerExceptionResolver.resolveException(request, response, null, authException)
                        )
                )
                .build();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
