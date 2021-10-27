package org.eden.lovestation.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
        httpServletResponse.setContentType("application/json");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());
        httpServletResponse.getWriter().write(String.format("{\"apiError\": {\n" +
                "        \"status\": \"%s\",\n" +
                "        \"timestamp\": \"%s\",\n" +
                "        \"message\": \"%s\"\n" +
                "    }}", HttpStatus.FORBIDDEN.getReasonPhrase(), dateStr, e.getMessage()));
    }
}
