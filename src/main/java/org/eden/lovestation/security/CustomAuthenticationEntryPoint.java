package org.eden.lovestation.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());
        httpServletResponse.getWriter().write(String.format("{\"apiError\": {\n" +
                "        \"status\": \"%s\",\n" +
                "        \"timestamp\": \"%s\",\n" +
                "        \"message\": \"%s\"\n" +
                "    }}", HttpStatus.FORBIDDEN.getReasonPhrase(), dateStr, "Access is denied"));
    }
}
