package com.bitc.board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.board.dto.BoardFileDto;

// @Component : 스프링프레임워크가 자동으로 등록하여 객체를 실행할 자바 클래스, @Bean과 동일한 기능을 가지고 있음, @Bean과의 차이점 @Bean은 스프링프레임워크에 기본 등록되어 있는 라이브러리이고, @Component는 사용자가 필요에 따라서 작성하여 추가하는 것
@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multiFiles) throws Exception {
		
//		매개변수로 받은 파일 정보가 없을 경우 null을 리턴
		if (ObjectUtils.isEmpty(multiFiles)) {
			return null;
		}
		
//		매개변수로 받은 파일 정보에서 BoardFileDto 클래스에서 사용하는 정보만 추려내어서 리스트로 생성하여 저장할 변수
		List<BoardFileDto> fileList = new ArrayList<>();
//		날짜 사용 형식을 지정하는 클래스
//		구글에서 DateTimeFormatter 검색하여 API 확인
//		참고 : https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
//		현재 시간을 현재 거주하는 지역의 시간대를 기준으로 하여 출력
		ZonedDateTime current = ZonedDateTime.now();
//		current.format(format) : 현재 날짜 및 시간을 출력 시 위에서 지정한 형식대로 출력한다는 명령
//		이미지 파일 저장경로 설정
		String path = "images/" + current.format(format);
//		File 클래스의 객체 file을 생성(위에서 지정한 경로를 바탕으로 생성)
		
//		서버 OS로 주로 사용하는 Linux나 Unix는 폴더 및 파일, 각종 디스크 드라이버를 모두 파일로 인식함
		File file = new File(path);
//		지정한 위치에 폴더가 존재하는지 확인
		if (file.exists() == false) {
			file.mkdirs(); // 폴더 생성
		}
		
//		매개변수로 받아온 파일 정보에서 모든 파일 이름을 가져옴
		Iterator<String> iterator = multiFiles.getFileNames();
		String newFileName;
		String originalFileExtension;
		String contentType;
		
		while(iterator.hasNext()) {
			String name = iterator.next();
//			지정한 파일명을 가지고 있는 파일의 모든 정보를 가져옴
			List<MultipartFile> list = multiFiles.getFiles(name);
			
			for (MultipartFile mFile : list) {
				if (mFile.isEmpty() == false) {
					contentType = mFile.getContentType();
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
//						파일 확장자명 설정
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if (contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if (contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
//					현재 시간을 기준으로 새로운 이름을 설정함
//					System.nanoTime() : 1970년 1월 1일 00 시 00분 00초를 기준으로해서 현재시간까지 진행된 시간을 1/1000로 표현
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					BoardFileDto boardFile = new BoardFileDto();
//					현재 메서드의 첫번째 매개변수로 받아온 게시물 번호를 저장
					boardFile.setBoardIdx(boardIdx);
//					현재 파일의 크기를 가져와서 저장(byte 단위)
					
//					MultipartFile 클래스의 getSize() 메서드의 반환타입이 long이기 때문에 SQL문에서 1메가가 넘어가는 경우 1,xxx로 표시되어 문자열로 인식이 되기 때문에 그대로 사용하지 못하고 문자열로 변환하여 사용
					boardFile.setFileSize(Long.toString(mFile.getSize()));
//					현재 파일의 실제 파일명을 가져와서 저장
					boardFile.setOriginalFileName(mFile.getOriginalFilename());
//					새로 생성된 파일명과 실제 디스크에 저장될 경로를 합하여 저장
					boardFile.setStoredFilePath(path + "/" + newFileName);
					
//					데이터베이스에 저장할 목록에 저장
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
//					현재 파일(메모리에만 존재)을 지정한 위치로 이동(실제 디스크에 저장)
					mFile.transferTo(file);
				}
			}
		}
		
//		모든 파일 정보를 가지고 있는 리스트를 반환
		return fileList;
	}
}












