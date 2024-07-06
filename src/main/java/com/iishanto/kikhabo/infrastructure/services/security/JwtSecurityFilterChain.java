package com.iishanto.kikhabo.infrastructure.services.security;

import ch.qos.logback.core.util.StringUtil;
import com.iishanto.kikhabo.common.exception.security.UnauthorizedAccessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@AllArgsConstructor
@Service
public class JwtSecurityFilterChain extends OncePerRequestFilter {
    JwtService jwtService;
    UserDetailsService userDetailsService;
    Logger logger;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("Filtering jwt request ip: {} -> path: {}",request.getRemoteAddr(),request.getPathInfo());
        String jwtToken=request.getHeader("Authorization");
        if(jwtToken!=null&& jwtToken.length()>7&&jwtToken.charAt(6)==' '){
            jwtToken=jwtToken.split(" ")[1];
            String email=jwtService.getUserEmailFromToken(jwtToken);
            UserDetails userDetails=userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response);
    }
}
