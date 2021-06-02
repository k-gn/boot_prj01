package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    // @Query : 조회 컬럼이 1개 이상일 경우 Object[], 1개일 경우 Object 또는 해당 타입으로 반환 가능
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "select b, w, count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "group by b",
           countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query(value = "select b, w, count(r) " +
            "from Board b " +
            "left join b.writer w " +
            "left join Reply r on r.board = b " +
            "where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
