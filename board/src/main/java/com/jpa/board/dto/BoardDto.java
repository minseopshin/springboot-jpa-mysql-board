package com.jpa.board.dto;

import com.jpa.board.domain.entity.Board;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class BoardDto {
	private Long num;
	private String title;
	private String context;
	
	public Board toEntity() {
		Board build = Board.builder()
				.num(num)
				.title(title)
				.context(context)
				.build();
		return build;
	}
	
	@Builder
	public BoardDto(Long num, String title, String context) {
		this.num = num;
		this.title = title;
		this.context = context;
	}
}
