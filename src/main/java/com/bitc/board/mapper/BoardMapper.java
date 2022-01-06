package com.bitc.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {

//	데이터 베이스 연결하여 게시글 목록을 불러오는 메서드
	List<BoardDto> selectBoardList() throws Exception;
	
//	DB와 연결하여 게시글을 등록하는 메서드
	void insertBoard(BoardDto board) throws Exception;
	
//	DB와 연결하여 지정한 게시글의 모든 정보를 가져오는 메서드
	BoardDto selectBoardDetail(int idx) throws Exception;
	
//	DB와 연결하여 지정한 게시글을 DB에서 삭제하는 메서드
	void deleteBoard(int idx) throws Exception;
	
//	DB와 연결하여 지정한 게시글을 DB에서 수정하는 메서드
	void updateBoard(BoardDto board) throws Exception;
	
//	DB와 연결하여 지정한 게시글을 조회수를 수정하는 메서드
	void updateHitCnt(int idx) throws Exception;

//	DB에 연결하여 게시글의 첨부파일 정보를 DB에 저장하는 메서드
	void insertBoardFileList(List<BoardFileDto> list) throws Exception;
	
//	DB에 연결하여 게시글의 첨부파일 정보를 가져오는 메서드
	List<BoardFileDto> selectBoardFileList(int idx) throws Exception;
	
	
//	DB에 연결하여 게시글의 첨부파일 정보를 가져오는 메서드
//	@Param : 매개변수로 받아온 파일에 대한 설명을 표시하는 어노테이션
//	mapper 에서 @Param으로 지정한 이름을 xml 파일에서 사용할 수 있음
	BoardFileDto selectBoardFileInfo(@Param("fileIdx") int fileIdx, @Param("boardIdx") int boardIdx) throws Exception;
}