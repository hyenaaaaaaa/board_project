package org.koreait.project.controllers.members;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.koreait.project.commons.MemberUtil;
import org.koreait.project.commons.Utils;
import org.koreait.project.entities.BoardData;
import org.koreait.project.entities.Member;
import org.koreait.project.models.member.MemberInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.security.Security;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Transactional
public class MemberController {
    private final Utils utils;
    private final MemberUtil memberUtil;
    private final EntityManager em;

    @GetMapping("/join") // 회원가입
    public String join() {

        return utils.tpl("member/join");
    }

    @GetMapping("/login") // 로그인
    public String login(String redirectURL, Model model) {
        model.addAttribute("redirectURL", redirectURL);
        return utils.tpl("member/login");
    }

    @ResponseBody
    @GetMapping("/info") // 로그인 시 로그에 정보 불러오기
    public void info() {
        BoardData data = BoardData.builder()
                .subject("제목")
                .content("내용")
                .build();

        em.persist(data);
        em.flush();

        data.setSubject("수정제목");
        em.flush();
    }
/*
    public void info() {
        MemberInfo member = (MemberInfo) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        log.info(member.toString());

   }
 */

    /*
    public void info(@AuthenticationPrincipal MemberInfo memberInfo) {
        log.info(memberInfo.toString());
    }
     */

    /*
    public void info(Principal principal) {
        String email = principal.getName();
        log.info(email);
    }
     */
}
