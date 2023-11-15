package org.koreait.project.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.koreait.project.commons.constants.MemberType;


import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = { // 검색 빈도가 높은 항목
        @Index(name = "idx_member_userNm", columnList = "userNm"),
        @Index(name = "idx_member_mobile", columnList = "mobile")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNo;

    @Column(length = 65, unique = true, nullable = false)
    private String email;

    @Column(length = 65, name = "pw", nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String userNm;

    @Column(length = 11, nullable = false)
    private String mobile;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType mtype = MemberType.USER;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(insertable = false)
    @UpdateTimestamp
    private LocalDateTime modDt;

    @Transient
    private String tmpData;

    /*
    @Temporal()
    private Date date;
     */
}
