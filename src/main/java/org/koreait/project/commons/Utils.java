package org.koreait.project.commons;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class Utils {
    private static ResourceBundle validationsBundle;
    private static ResourceBundle errorsBundle;
    private final HttpServletRequest request;
    private final HttpSession session;

    static {
        validationsBundle = ResourceBundle.getBundle("messages.validation");
        errorsBundle = ResourceBundle.getBundle("messages.errors");

    }

    public static String getMessage(String code, String bundleType) {
        bundleType = Objects.requireNonNullElse(bundleType, "validation");
        ResourceBundle bundle = bundleType.equals("error") ? errorsBundle : validationsBundle;
        try {
            return bundle.getString(code);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isMobile() { // 접속한 것이 모바일인지 pc인지 판별

        String device = (String) session.getAttribute("device");
        if (device != null) {
            return device.equals("mobile");
        }

        //요청 헤더 User-Agent
        boolean isMobile = request.getHeader("User-Agent").matches(".*(iPhone|iPod|iPad|BlackBerry|Android|Windows CE|LG|MOT|SAMSUNG|SonyEricsson).*");


        return isMobile;
    }

    public String tpl(String tplPath) { // 접속한 것이 모바일인지 pc인지 판별 후 템플릿 결정
        return String.format("%s/" + tplPath, isMobile() ? "mobile" : "front");
    }
}
