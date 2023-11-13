package org.koreait.project.controllers.members;


import lombok.RequiredArgsConstructor;
import org.koreait.project.commons.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final Utils utils;

    @GetMapping("/join") // 회원가입
    public String join() {

        return utils.tpl("member/join");
    }
    
    @GetMapping("/login") // 로그인
    public String login(String redirectURL, Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return utils.tpl("member/login");
    }
}
