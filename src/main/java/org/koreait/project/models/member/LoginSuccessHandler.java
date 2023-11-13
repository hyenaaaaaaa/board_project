package org.koreait.project.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.project.commons.Utils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Objects;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        Utils.loginInit(session);

        /* 사용자가 입력한 정보에 대한 응답을 하기 위함 (성공시)
        *
        * 요청 데이터 redirectURL값이 있으면 이동 없으면 메인페이지(/)
        * */

        String redirectURL = Objects.requireNonNullElse(request.getParameter("redirectURL"), "/");
        response.sendRedirect(request.getContextPath()+redirectURL);

    }
}
