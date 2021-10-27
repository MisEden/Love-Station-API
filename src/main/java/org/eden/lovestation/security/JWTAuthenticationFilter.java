package org.eden.lovestation.security;

import org.eden.lovestation.exception.business.InvalidJWTException;
import org.eden.lovestation.util.jwt.JWTUtil;
import org.eden.lovestation.util.jwt.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    public JWTAuthenticationFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("x-eden-token");
        if (authHeader != null) {
            try {
                Payload payload = jwtUtil.verify(authHeader);
                if (payload.getUserId() != null && payload.getRole() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(payload.getRole()));

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(payload.getUserId(), null, authorities);

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                chain.doFilter(request, response);
            } catch (InvalidJWTException e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateStr = simpleDateFormat.format(new Date());
                response.getWriter().write(String.format("{\"apiError\": {\n" +
                        "        \"status\": \"%s\",\n" +
                        "        \"timestamp\": \"%s\",\n" +
                        "        \"message\": \"%s\"\n" +
                        "    }}", HttpStatus.UNAUTHORIZED.getReasonPhrase(), dateStr, e.getMessage()));
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
