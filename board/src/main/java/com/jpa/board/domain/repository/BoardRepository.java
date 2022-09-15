package com.jpa.board.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpa.board.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
	List<Board> findByTitleContaining(String keyword);

}
