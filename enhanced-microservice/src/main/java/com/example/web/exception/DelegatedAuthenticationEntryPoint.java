package com.example.web.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (response.getHeader("access_denied_reason") == null) {
            response.addHeader("access_denied_reason", "authentication_required");
        }
        response.sendError(403, "Access Denied");
    }
}
