package com.bitc.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.BoardDto;
import com.bitc.board.dto.BoardFileDto;
import com.bitc.board.service.BoardService;

// 해당 클래스가 MVC모델에서 control 부분을 담당하는 파일이라는 것을 알려주는 어노테이션
// @Controller : 일반적인 control 부분을 담당하는 어노테이션(클라이언트에 html 파일을 전송)
// @RestController : Restful API 방식을 사용할 경우 사용하는 어노테이션 (클라이언트에 데이터 자체를 전송)
@Controller
public class BoardController {

	@Autowired
//	비즈니스 로직을 처리하는 서비스 빈과 연결
	private BoardService boardService;
	
//	사용자가 접속할 수 있는 주소를 해당 메서드와 연결하는 어노테이션
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
//		ModelAndView 클래스 : 데이터와 화면을 동시에 가지고 있는 클래스
//		해당 클래스 타입을 반환하면 지정한 html 파일에 데이터를 포함하여 클라이언트에게 전송
//		ModelAndView 클래스 생성자의 매개변수로 html 템플릿 파일의 위치를 지정함
//		html 템플릿의 위치는 /src/main/resources/templates 를 기본으로 함
		ModelAndView mv = new ModelAndView("/board/boardList");
		
//		서비스 빈의 selectBoardList() 메서드를 실행하여 실제 데이터 목록을 가져옴
		List<BoardDto> boardList = boardService.selectBoardList();
		int arrays[] = {1, 2, 3, 4, 5};
//		가져온 데이터 목록을 ModelAndView 클래스 타입의 객체에 추가
		mv.addObject("boardList", boardList);
		mv.addObject("arr", arrays);
		
//		클라이언트에 ModelAndView 클래스 타입의 객체를 전송
		return mv;
	}
	
//	클라이언트 요청 주소와 메서드를 연동
//	웹브라우저의 주소창에 서버주소:포트번호/board/writeBoard.do 를 입력하면 연결된 writeBoard() 메서드가 실행됨
	@RequestMapping("/board/writeBoard.do")
	public String writeBoard() throws Exception {
//		반환 타입이 String일 경우 기본적으로 templates 폴더에 있는 템플릿파일을 지정함
//		templates 폴더 아래에 있는 /board/writeBoard.html 파일을 지정함
		return "/board/writeBoard";
	}
	
//	/board/insertBoard.do와 연결된 insertBoard() 메서드를 실행
//	매개변수로 클라이언트에서 BoardDto의 객체 board를 받아옴
//	Spring framework에서 미리 생성한 BoardService 타입의 객체 boardService의 insertBoard() 메서드를 실행
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multiFiles) throws Exception {

		boardService.insertBoard(board, multiFiles);
		
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam("idx") int idx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(idx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(@RequestParam("idx") int idx) throws Exception {
		boardService.deleteBoard(idx);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam("fileIdx") int fileIdx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
		BoardFileDto boardFile = boardService.selectBoardFileInfo(fileIdx, boardIdx);
		
		if (ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		
		}
	}
}













