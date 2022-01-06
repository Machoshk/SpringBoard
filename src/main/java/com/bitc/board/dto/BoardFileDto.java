package com.bitc.board.dto;

import lombok.Data;

@Data
public class BoardFileDto {
	
	private int fileIdx; 
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	
}
