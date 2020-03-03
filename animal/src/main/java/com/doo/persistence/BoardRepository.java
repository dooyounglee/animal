package com.doo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	public Iterable<Board> findBoardByDelYN(String delYN);
	
	@Query("select distinct b,m, count(r_no) as rcount" + 
			"  from Board b" + 
			" left join Reply r on (b.b_no=r.board)" + 
			" left join Member m on (b.writer=m.email and b.pw is null)" + 
			" where b.delYN='N'" + 
			"   and r.delYN='N'" +
			" group by b.b_no")
	public List<Object[]> findAllBoardInListPage();
	
	@Query("select b, count(r_no) as rCount" + 
			"  from Board b" + 
			" left join Reply r on(r.board=b.b_no)" +
			" where b.delYN='N'" +
			" group by b.b_no")
	public List<Object[]> findBoardByDelYNWithReplyCountNative();
	
	@Query("select b,m" + 
			"  from Board b" +
			" left join Member m on (b.writer=m.email and b.pw is null)" + 
			" where b.delYN='N'" + 
			"   and b.b_no=?1")
	public List<Object[]> findBoardInGetPage(Long b_no);
//	@Query(value="select b.*, count(r_no) as rcount" + 
//			"  from board b" + 
//			" left join reply r on(r.board_b_no=b.b_no)" + 
//			" group by b.b_no",nativeQuery=true)
//	public List<Object[]> findBoardByDelYNWithReplyCountNative();
}
