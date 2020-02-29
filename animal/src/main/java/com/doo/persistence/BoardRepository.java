package com.doo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.doo.vo.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

	public Iterable<Board> findBoardByDelYN(String delYN);
}
