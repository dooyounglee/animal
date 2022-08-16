package com.doo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.doo.vo.VoteAnswer;
import com.doo.vo.dto.OpinionAnswer;

public interface VoteAnswerRepository extends CrudRepository<VoteAnswer, OpinionAnswer>{

}
