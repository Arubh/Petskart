package com.example.demo.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class AuthTokenFilter extends OncePerRequestFilter {
	    @Autowired
	    private UserService jwtUserDetailsService;
	    @Autowired
	    private JwtUtils jwtTokenUtil;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request,
	            HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {

	        // TODO Auto-generated method stub

	        final String requestTokenHeader = request.getHeader("Authorization");
	        String username = null;
	        String jwtToken = null;
	        
	        // JWT Token is in the form "Bearer token". Remove Bearer word
	        // and get only the
	        // Token
	        System.out.println("Token is "+requestTokenHeader);
	        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	            jwtToken = requestTokenHeader.substring(7);

	            try {

	                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
	            } catch (IllegalArgumentException e) {
	                System.out.println("Unable to get JWT Token");
	            } catch (ExpiredJwtException e) {
	                System.out.println("JWT Token has expired");
	            }
	        } else {
	            System.out.println("Token is invalid");
	        }

	        // Once we get the token validate it.

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
	            // if token is valid configure Spring Security to manually set authentication
	            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null,
	                        userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	                // After setting the Authentication in the context, we specify
	                // that the current user is authenticated. So it passes the Spring Security
	                // Configurations successfully.
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        } else {
	            System.out.println("Invalid token ");
	        }
	        filterChain.doFilter(request, response);
	    }
	}