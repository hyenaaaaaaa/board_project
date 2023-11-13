package org.koreait.project.models.member;

import lombok.Builder;
import lombok.Data;
import org.koreait.project.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Data @Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String password;

    private Member member;

    private Collection<? extends GrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 권한에 대한 범위
        return authorities;
    }

    @Override
    public String getPassword() { // 로그인 할 때 사용
        return password;
    }

    @Override
    public String getUsername() { // 로그인 할 때 사용
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { // 만료 여부 판단
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠김 여부 판단
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
        return true;
    }

    @Override
    public boolean isEnabled() { // 회원 탈퇴 여부 판단
        return true;
    }
}
