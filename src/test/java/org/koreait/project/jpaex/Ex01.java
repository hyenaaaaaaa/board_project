package org.koreait.project.jpaex;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koreait.project.commons.constants.MemberType;
import org.koreait.project.entities.Member;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
//@TestPropertySource(activeProfiles="test")
public class Ex01 {

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void init() {
        Member member = new Member();
        //member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01010001000");
        member.setMtype(MemberType.USER);


        em.persist(member);
        em.flush();
        em.clear(); // 영속성 비우기 - 상태 변경
    }

    @Test
    void test2() { // find : primary키를 이용한 조회
        Member member = em.find(Member.class, 1L); // DB -> 영속성 컨텍스트로 추가
        System.out.println(member);

        Member member2 = em.find(Member.class, 1L); // 영속성 컨텍스트에서 조회
        System.out.println(member2);

        // 엔티티의 이름을 가지고 조회 - 복잡한 조회 -> 쿼리 생성 후 조회

        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member AS m WHERE m.email LIKE :key", Member.class);
        query.setParameter("key", "%user%");
        Member member3 = query.getSingleResult();

        member3.setUserNm("(수정)사용자01");
        em.flush();
    }

    @Test
    void test1() {
        Member member = new Member();
        //member.setUserNo(1L);
        member.setEmail("user01@test.org");
        member.setUserNm("사용자01");
        member.setPassword("123456");
        member.setMobile("01010001000");
        member.setMtype(MemberType.USER);


        em.persist(member); // 변화 감지 상태, 영속성으로 변환
        em.flush(); // DB에 반영

        em.detach(member); // 영속성 분리, 변화 감지 X

        member.setUserNm("(수정)사용자01"); // 업데이트(수정)
        em.flush();

        em.merge(member); // 분리된 영속 상태를 하나의 영속 상태로 합쳐줌
        em.flush();

        // em.remove(member); // 삭제
        // em.flush();


    }

    @Test
    void test3() {

    }

}
