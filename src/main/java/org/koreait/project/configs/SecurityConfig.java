package org.koreait.project.configs;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.koreait.project.models.member.LoginFailureHandler;
import org.koreait.project.models.member.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
public class SecurityConfig {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // 로그인을 시도 했을 때 상황에 맞게 홈페이지 연결
        /* 인증 설정 - 로그인 S*/
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler()) // 성공했을 때 이동할 경로
                    .failureHandler(new LoginFailureHandler()); // 실패했을 때 이동할 경로
        }); // DSL

        /* 로그아웃 S */
        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        /* 로그아웃 E */
        /* 인증 설정 - 로그인 E*/

        http.headers(c -> {
            c.frameOptions(o -> o.sameOrigin());
        });

        /* 인가 설정 - 접근 통제 S*/
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated() // 회원 전용 (로그인 한 회원만 접근 가능)
                    .requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자 전용
                    .anyRequest().permitAll(); // 권한이 필요 없는 나머지 페이지
        });

        http.exceptionHandling(c -> { // 인증 실패 시 유입되는 공간
            c.authenticationEntryPoint((req, resp, e) -> {
                String URI = req.getRequestURI();
                if (URI.indexOf("/admin") != -1) { // 관리자 페이지 -> 401 응답 코드
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                } else { // 회원 전용 페이지 ( /mypage ) -> 로그인 페이지로 이동
                    String url = req.getContextPath() + "/member/login";
                    resp.sendRedirect(url);
                }
            });
        });

        /* 인가 설정 - 접근 통제 E*/

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 시큐리티 설정이 적용될 필요가 없는 경로 설정

        return w -> w.ignoring().requestMatchers(
                "/front/css/**",
                "/front/js/**",
                "/front/images/**",

                "/mobile/css/**",
                "/mobile/js/**",
                "/mobile/images/**",

                "/admin/css/**",
                "/admin/js/**",
                "/admin/images/**",

                "/common/css/**",
                "/common/js/**",
                "/common/images/**",
                fileUploadConfig.getUrl() + "/**");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }
}
