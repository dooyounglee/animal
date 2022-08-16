package com.doo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.doo.vo.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {

//	@Query("select v " + 
//			"  from Vote v " + 
//			"  join VoteOpinion v1 on (v.vno=v1.vno) ")
//	public List<Object[]> getVoteWithCnt(Long vno);
	
	@Query("select v from Vote v where v.vno=:name")
	public List<Vote> getVote(@Param("name") Long vno);
	
}
