package com.jpa.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jpa.board.domain.entity.Board;
import com.jpa.board.domain.repository.BoardRepository;
import com.jpa.board.dto.BoardDto;

@Service
public class BoardService {
	
	private BoardRepository boardRepository;
	private static final int BLOCK_PAGE_NUM_COUNT = 10;
	private static final int PAGE_POST_COUNT = 10;
	
	public BoardService(BoardRepository boardRepository)	{
		this.boardRepository = boardRepository;
	}
	
	@Transactional
	public void savePost(BoardDto boardDto)	{
		boardRepository.save(boardDto.toEntity()).getNum();
	}
	
	@Transactional
	public List<BoardDto> getBoardList(Integer PageNum)	{
		Page<Board> page = boardRepository.findAll(PageRequest.of(PageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC,"num")));
		
//		List<Board> boards = boardRepository.findAll();
		List<Board> boards = page.getContent();
		List<BoardDto> boardDtoList = new ArrayList<>();
		
		for (Board board : boards) {
			BoardDto boardDto = BoardDto.builder()
					.num(board.getNum())
					.title(board.getTitle())
					.context(board.getContext())
					.build();
			
			boardDtoList.add(boardDto);
		}
		return boardDtoList;
	}
	
	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];
		
		// 총 게시글 수
		Double postsTotalCount = Double.valueOf(this.getBoardCount());
		// 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
		Integer totalLastPageNum = (int) (Math.ceil(postsTotalCount/PAGE_POST_COUNT));
		// 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
		Integer blackLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
				? curPageNum + BLOCK_PAGE_NUM_COUNT
				: totalLastPageNum;
		// 페이지 시작 번호 조정
		curPageNum = (curPageNum <=3) ? 1 :curPageNum-2;
		// 페이지 번호 할당
		for(int val = curPageNum, i=0; val<=blackLastPageNum; val++, i++)	{
			pageList[i] = val;
		}
		return pageList;
	}
	
	@Transactional
	public Long getBoardCount() {
		return boardRepository.count();
	}
	
	@Transactional
	public BoardDto getPost(Long id)	{
		System.out.print(id);
		Optional<Board> boardWrapper = boardRepository.findById(id);
		Board board = boardWrapper.get();
		
		BoardDto boardDto = BoardDto.builder()
				.num(board.getNum())
				.title(board.getTitle())
				.context(board.getContext())
				.build();
		
		return boardDto;	
	}
	
	@Transactional
	public void deletePost(Long id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public List<BoardDto> searchPosts(String keyword)	{
		List<Board> boards = boardRepository.findByTitleContaining(keyword);
		List<BoardDto> boardDtoList = new ArrayList<>();
		
		if (boards.isEmpty()) return boardDtoList;
		
		for(Board board :boards)	{
			boardDtoList.add(this.convertEntityToDto(board));
		}
		return boardDtoList;
	}
	
	private BoardDto convertEntityToDto(Board board) {
		return BoardDto.builder()
				.num(board.getNum())
				.title(board.getTitle())
				.context(board.getContext())
				.build();
	}
	
}
