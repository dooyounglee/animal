package com.doo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	public Iterable<Board> findBoardByDelYN(String delYN);
	
	@Query( "select b, count(1) as rCount" + 
			"  from Board b" + 
			" left join Reply r on(r.board=b.b_no)" +
			" where b.delYN='N'" +
			" group by b.b_no")
	public Iterable<Board> findBoardByDelYNWithReplyCount();
	
	@Query("select b, count(r_no) as rCount" + 
			"  from Board b" + 
			" left join Reply r on(r.board=b.b_no)" +
			" where b.delYN='N'" +
			" group by b.b_no")
	public List<Object[]> findBoardByDelYNWithReplyCountNative();
	
//	@Query(value="select b.*, count(r_no) as rcount" + 
//			"  from board b" + 
//			" left join reply r on(r.board_b_no=b.b_no)" + 
//			" group by b.b_no",nativeQuery=true)
//	public List<Object[]> findBoardByDelYNWithReplyCountNative();
}
