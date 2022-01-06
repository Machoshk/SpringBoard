package com.bitc.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.MemberDto;

@Controller
public class ThymeleafController {
	
	@RequestMapping("/thymeleaf")
	public String thymeleafIndex() {
		return "/thymeleaf/index";
	}
	
	@RequestMapping("/thymeleaf/value1")
	public ModelAndView thymeleafValue1() throws Exception {
		ModelAndView mv = new ModelAndView("/thymeleaf/value1");
		
		MemberDto member = new MemberDto();
		member.setUserId("tester01");
		member.setUserPw("bitc1234");
		member.setUserName("테스터01");
		member.setUserEmail("tester01@bitc.co.kr");
		
		mv.addObject("member", member);
		
		
		return mv;
	}
}
