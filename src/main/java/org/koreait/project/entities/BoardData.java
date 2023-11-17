package org.koreait.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.project.entities.BaseMember;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
public class BoardData extends BaseMember {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(length=100, nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="userNo")
    private Member member;

}