package com.doo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Board;
import com.doo.vo.Reply;

public interface ReplyRepository extends CrudRepository<Reply, Long>{

	@Query("SELECT r FROM Reply r WHERE r.board = ?1 " +
		       " AND r.r_no > 0 ORDER BY r.r_no ASC")
		public List<Reply> getReplyOfBoard(Board board);
}
