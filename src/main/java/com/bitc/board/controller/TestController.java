package com.bitc.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.board.dto.CalculatorDto;
import com.bitc.board.dto.PersonDto;


// 사용자의 요청을 처리하는 곳
@Controller
public class TestController {

//	@Controller 어노테이션을 사용하고 
//	@RequestMapping이 적용된 메서드의 반환 타입을 String으로 지정햇을 경우 템플릿의 위치를 지정한 것으로 간주함
//	반환 타입을 String으로
	@RequestMapping("/test/test1")
	public String test1() throws Exception {
		return "test/test1";
	}
	@RequestMapping("/test/testsh")
	public String testsh() throws Exception {
		return "test/testsh";
	}
	
	
	
	
//	사용자가 form 태그를 통하여 서버로 데이터를 전송할 경우 스프링부트에서 데이터를 받는 방법
//	1. DTO 클래스를 생성하여 데이터를 받는 방식
//  -> form 태그가 가지고 있는 input 태그의 name 속성값을 DTO클래스가 가지고 있는 멤버 변수명과 동일하게 사용
//	2. @RequestParam 어노테이션을 사용하여 데이터를 받는 방식
	@RequestMapping("/test/Calculator.do")
	public String inputPage()	{
		return "test/Calculator";
	}
	@RequestMapping("/test/CalculatorProcess.do")
	public ModelAndView inputPageProcess(CalculatorDto dto) {
		ModelAndView mv = new ModelAndView("/test/CalculatorProcess");
		
		dto.setResult(dto.getNum1() + dto.getNum2());
		
		mv.addObject("data", dto);
		
		return mv;	
	}
	
	@RequestMapping("/test/CalculatorProcess2.do")
	public ModelAndView inputPageProcess(@RequestParam("num1") int num1, @RequestParam("num2") int num2) {
		ModelAndView mv = new ModelAndView("test/CalculatorProcess2");
		
		int result = num1 + num2;
		
		mv.addObject("num1", num1);
		mv.addObject("num2", num2);
		mv.addObject("result", result);
				
		return mv;
	}
	
	@RequestMapping("/test/Signin.do")
	public String Signin() {
		return "/test/Signin";
	}
	
	@RequestMapping("/test/SigninProcess.do")
	public ModelAndView SigninProcess(PersonDto dto) {
		ModelAndView mv = new ModelAndView("test/SigninProcess");
		
		mv.addObject("data", dto);
		
		return mv;	
	}
	
	@RequestMapping("/test/SigninProcess2.do")
	public ModelAndView SigninProcess(@RequestParam("seq") String seq, 
			@RequestParam("name") String name, @RequestParam("id") String id,
			@RequestParam("age") String age, @RequestParam("pw") String pw, 
			@RequestParam("email") String email) {
		ModelAndView mv = new ModelAndView("test/SigninProcess2");
		
		mv.addObject("seq", seq);
		mv.addObject("name", name);
		mv.addObject("age", age);
		mv.addObject("id", id);
		mv.addObject("pw", pw);
		mv.addObject("email", email);
				
		return mv;
	}
}
