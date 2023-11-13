package org.koreait.project.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.project.commons.Utils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException { // 사용자가 입력한 정보에 대한 응답을 하기 위함 (실패시)

        HttpSession session = request.getSession(); // 세션에서 가져오기

        Utils.loginInit(session);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isRequiredfieldCheck = false;

        session.setAttribute("email", email);

        /* 필수 항목 검증 - email, password S */
        if (email == null || email.isBlank()) {
            session.setAttribute("NotBlank_email", Utils.getMessage("NotBlank.email", "validation"));
            isRequiredfieldCheck = true;
        }

        if (password == null || password.isBlank()) {
            session.setAttribute("NotBlank_password", Utils.getMessage("NotBlank.password", "validation"));
            isRequiredfieldCheck = true;
        }
        /* 필수 항목 검증 - email, password E */

        if (!isRequiredfieldCheck) { // 아이디가 없거나 비밀번호가 잘못된 경우
            session.setAttribute("globalError", Utils.getMessage("Login.fail", "validation"));
        }


        response.sendRedirect(request.getContextPath() + "/member/login");
    }

}
