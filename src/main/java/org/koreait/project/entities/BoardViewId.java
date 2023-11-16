package org.koreait.project.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Entity
@IdClass(BoardViewId.class)
public class BoardViewId {

    @Id
    private Long seq;

    @Id
    @Column(name="_uid")
    private Integer uid;

}
