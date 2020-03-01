package com.doo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Board;
import com.doo.vo.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long>{

	@Query( "select r,m" + 
			"   from Reply r" + 
			" left join Member m on (r.replyer=m.email and r.pw is null)" + 
			" where r.delYN='N'" +
			"   and r.board = ?1" +
			"   AND r.rno > 0" +
			" ORDER BY r.rref ASC, r.rno ASC")
	public List<Object[]> getReplyOfBoard(Board board);
	
	public Reply findTop1ByOrderByRnoDesc();
}
