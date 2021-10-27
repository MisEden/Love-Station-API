package org.eden.lovestation.security;

import org.eden.lovestation.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthServiceImpl authService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/storage/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        //允許跨網域請求的來源
        configuration.setAllowedOrigins(List.of("*", "/*", "https://cdn.pannellum.org"));

        //允許使用那些請求方式
        configuration.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        //禁用跨域攜帶cookie資訊，預設跨網域請求是不攜帶cookie資訊的。
        configuration.setAllowCredentials(false);

        //允許哪些Header
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "x-eden-token", "x-eden-identity-card"));

        //映射路徑
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v1/api/health").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/api/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/auth/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable", "referral_employee", "user", "landlord", "volunteer", "firm_employee")
                .antMatchers("/v1/api/passwords/**").permitAll()
                // Admin
                .antMatchers(HttpMethod.POST, "/v1/api/admin").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/admins/index/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers("/v1/api/admins/password/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers(HttpMethod.GET, "/v1/api/admins/checkin-applications/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers(HttpMethod.PATCH, "/v1/api/admins/checkin-applications/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.GET, "/v1/api/admins/check-account/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers(HttpMethod.PATCH, "/v1/api/admins/check-account/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.GET, "/v1/api/admins/role/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers(HttpMethod.POST, "/v1/api/admins/partners/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.PUT, "/v1/api/admins/partners/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.GET, "/v1/api/admins/feedback/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers(HttpMethod.GET, "/v1/api/admins/service/**").hasAnyAuthority("admin", "admin_readonly", "admin_writable")
                .antMatchers(HttpMethod.POST, "/v1/api/admins/service/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.POST, "/v1/api/admins/houses/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.PUT, "/v1/api/admins/houses/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.DELETE, "/v1/api/admins/houses/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.POST, "/v1/api/admins/furniture/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.PUT, "/v1/api/admins/furniture/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers(HttpMethod.DELETE, "/v1/api/admins/furniture/**").hasAnyAuthority("admin", "admin_writable")
                .antMatchers("/v1/api/admins/authority/**").hasAuthority("admin")
                .antMatchers(HttpMethod.DELETE, "/v1/api/admins/**").hasAuthority("admin")
                // Referral
                .antMatchers(HttpMethod.GET, "/v1/api/referrals").permitAll()
                // Referral Title
                .antMatchers(HttpMethod.GET, "/v1/api/referral-titles").permitAll()
                // Referral Employee
                .antMatchers(HttpMethod.POST, "/v1/api/referral-employees").permitAll()
                .antMatchers("/v1/api/referral-employees/**").hasAuthority("referral_employee")
                // User
                .antMatchers(HttpMethod.POST, "/v1/api/users").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/users/chinese-names").hasAnyAuthority("admin", "admin_readonly", "admin_writable", "referral_employee")
                // Landlord
                .antMatchers(HttpMethod.POST, "/v1/api/landlords").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/landlords/**").hasAnyAuthority("landlord")
                .antMatchers(HttpMethod.POST, "/v1/api/landlords/**").hasAnyAuthority("landlord")
                // Volunteer
                .antMatchers(HttpMethod.POST, "/v1/api/volunteers").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/volunteers/**").hasAnyAuthority("volunteer")
                .antMatchers(HttpMethod.POST, "/v1/api/volunteers/**").hasAnyAuthority("volunteer")
                // Firm
                .antMatchers(HttpMethod.GET, "/v1/api/firms/**").permitAll()
                // Firm Employee
                .antMatchers(HttpMethod.POST, "/v1/api/firm-employees").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/firm-employees/**").hasAnyAuthority("firm_employee")
                .antMatchers(HttpMethod.POST, "/v1/api/firm-employees/**").hasAnyAuthority("firm_employee")
                // House
                .antMatchers(HttpMethod.GET,"/v1/api/houses/**").permitAll()
                // Room
                .antMatchers(HttpMethod.GET,"/v1/api/rooms/{\\d+}/reservations/dates").hasAnyAuthority("admin", "admin_readonly", "admin_writable", "referral_employee", "user")
                .antMatchers(HttpMethod.GET,"/v1/api/rooms/**").permitAll()
                // Furniture
                .antMatchers(HttpMethod.GET,"/v1/api/furniture/**").permitAll()
                // Storage
                .antMatchers(HttpMethod.GET, "/v1/api/storage/**").permitAll()
                // Checkin Application
                .antMatchers(HttpMethod.POST, "/v1/api/checkin-applications/first-stage").hasAuthority("referral_employee")
                .antMatchers(HttpMethod.POST, "/v1/api/checkin-applications/{\\d+}/first-stage/files").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/user/search").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/referral-employee/search").hasAuthority("referral_employee")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/landlord/search").hasAuthority("landlord")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/volunteer/search").hasAuthority("volunteer")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/firm-employee/search").hasAuthority("firm_employee")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/{\\d+}/detail").hasAnyAuthority("user", "referral_employee")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/finished/latest").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin-applications/identity-card/finished/latest").hasAuthority("referral_employee")
                .antMatchers(HttpMethod.POST,"/v1/api/checkout-applications/{\\d+}/roomState/new").hasAuthority("user")
                // Checkin
                .antMatchers(HttpMethod.GET, "/v1/api/checkin/eligible/{\\d+}").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/v1/api/checkin/room/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/api/checkin/").hasAuthority("user")
                // Checkout
                .antMatchers(HttpMethod.GET, "/v1/api/checkout-applications/status").hasAuthority("user")
                .antMatchers(HttpMethod.GET, "/v1/api/checkout-applications/**").hasAuthority("user")
                .antMatchers(HttpMethod.POST, "/v1/api/checkout-applications/**").hasAuthority("user")
                // Notification
                .antMatchers("/v1/api/notifications/**").hasAnyAuthority("admin", "admin_writable")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        http.cors();
    }

}
