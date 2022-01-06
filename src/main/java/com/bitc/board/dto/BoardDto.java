package com.bitc.board.dto;

import java.util.List;

import lombok.Data;

// 롬복 사용
@Data
public class BoardDto {

	private int idx;
	private String title;
	private String contents;
	private String creatorId;
	private String createdDate;
	private String updatedId;
	private String updatedDate;
	private int hitCnt;
	private String passwd;
	// 첨부파일에 대한 정보를 저장하기 위한 멤버 변수를 추가함
	private List<BoardFileDto> fileList;	
}
