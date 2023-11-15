package org.koreait.project.jpaex;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.koreait.project.commons.constants.MemberType;
import org.koreait.project.entities.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
public class Ex1 {

    @PersistenceContext
    private EntityManager em;

    @Test
    void test1() {
        Member member = new Member();
        member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01010001000");
        member.setMtype(MemberType.USER);


        em.persist(member); // 변화 감지 상태
        em.flush();

        em.detach(member); // 영속성 분리, 변화 감지 X

        member.setUserNm("(수정)사용자01"); // 업데이트(수정)
        em.flush();
        
        em.merge(member); // 분리된 영속 상태를 하나의 영속 상태로 합쳐줌
        em.flush();

       // em.remove(member); // 삭제
       // em.flush();


    }

}
