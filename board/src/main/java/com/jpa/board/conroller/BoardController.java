package com.jpa.board.conroller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jpa.board.dto.BoardDto;
import com.jpa.board.service.BoardService;


@Controller
public class BoardController {
	
	private BoardService boardService;
	
	public BoardController(BoardService boardService)	{
		this.boardService = boardService;
	}
	
	@GetMapping("/")
	public String list(Model model, @RequestParam(value="page", defaultValue = "1")Integer PageNum) {
		List<BoardDto> boardDtoList = boardService.getBoardList(PageNum);
		Integer[] pageList = boardService.getPageList(PageNum);
		
		model.addAttribute("boardList",boardDtoList);
		model.addAttribute("pageList",pageList);
		return "board/list.html";
	}
	
	@GetMapping("/post")
	public String write() {
		return "board/write.html";
	}
	
	@PostMapping("/post")
	public String write(BoardDto boardDto) {
		boardService.savePost(boardDto);
		return "redirect:/";
	}
	
	@GetMapping("/post/{no}")
	public String detail(@PathVariable("no") Long id, Model model) {
		BoardDto boardDto = boardService.getPost(id);
		model.addAttribute("boardDto", boardDto);
		return "board/detail.html";
	}
	
	@GetMapping("/post/edit/{no}")
	public String edit(@PathVariable("no") Long id, Model model) {
		BoardDto boardDto = boardService.getPost(id);
		model.addAttribute("boardDto",boardDto);
		return "board/update.html";
	}
	
	@PutMapping("/post/edit/{no}")
	public String update(BoardDto boardDto) {
		boardService.savePost(boardDto);
		return "redirect:/";
	}
	
	@DeleteMapping("/post/{no}")
	public String delete(@PathVariable("no") Long id)	{
		boardService.deletePost(id);
		return "redirect:/";
	}
	
	@GetMapping("/board/search")
	public String search(@RequestParam(value="keyword") String keyword, Model model)	{
		List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
		model.addAttribute("boardList",boardDtoList);
		return "board/list";
	}
}
