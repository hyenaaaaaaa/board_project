package org.koreait.project.repositories;

import org.koreait.project.entities.BoardData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData, Long>, QuerydslPredicateExecutor<BoardData> {

    List<BoardData> findByCreatedAtBetween(LocalDateTime sdate, LocalDateTime edate, Pageable pageable);

    List<BoardData> findBySubjectContainingOrContentContainingOrderBySeqDesc (String subject, String Content);

    @Query("SELECT b FROM BoardData b WHERE b.subject LIKE :key1 OR b.content LIKE :key2 ORDER BY b.seq DESC" )
    List<BoardData> getList(@Param("key1") String subject, @Param("key2") String content);
}
