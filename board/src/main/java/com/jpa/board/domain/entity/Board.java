package com.jpa.board.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="board")
public class Board {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	@Column
	private String title;
	@Column
	private String context;
	
	@Builder
	public Board(Long num, String title, String context)	{
		this.num = num;
		this.title = title;
		this.context = context;
	}
	
	
}
