package com.spring.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.db.model.ScoreVO;
import com.spring.db.service.IScoreService;

@Controller
@RequestMapping("/score")	
public class ScoreController {

	// 컨트롤러와 서비스 계층 사이의 의존성 자동 주입을 위해 변수 선언
	// @Autowired는 스프링 컨테이너에 등록되어 있는 빈들 중, 해당 변수 타입에 맞는
	// 객체를 자동으로 변수에 주입해 주는 아노테이션 입니다.
	// 필드, 생성자, setter 메서드에 붙일 수 있습니다.
	@Autowired
	private IScoreService service;
	
//	@Autowired
//	public ScoreController(IScoreService service) {
//		this.service = service;
//	}
	
	
	// 점수 등록 화면을 열어주는 처리를 하는 메서드
	@GetMapping("/register")
	public String register() {
		System.out.println("/score/register: GET");
		return "score/write-form";
	}
	
	// 점수 등록 요청을 처리할 메서드
	@PostMapping("/register")
	public String register(ScoreVO vo) {
		System.out.println("/score/register: POST");
		System.out.println("vo: " + vo);
		service.insertScoSre(vo);
		return "score/write-result";
	}
	
	// 점수 전체를 조회를 처리하는 요청 메서드
	@GetMapping("/list")
	public void list(Model model) {
		System.out.println("/score/list: GET");
		List<ScoreVO> list = service.selectAllScores();
		model.addAttribute("sList", list);
		
	}
	
	// 점수 개별 조회 페이지 요청 메서드
	@GetMapping("/search")
	public void search() {
		System.out.println("/scroe/search: GET");
	}
	
	// 점수 개별 조회 처리 메서드
	@GetMapping("/selectOne")
	public String selectOne(int stuId, Model model, RedirectAttributes ra) {
		
		ScoreVO vo = service.SelectOne(stuId);
		
		if(vo == null) {
			ra.addFlashAttribute("msg", "학번 정보가 없습니다.");
			return "redirect:/score/search";
		} else {
			model.addAttribute("sut", vo);
			return "score/search-result";
		}
		
	}
	
	@GetMapping("/delete")
	public String delete(int stuId, RedirectAttributes ra) {
		System.out.println("삭제할 학번: " + stuId);
		service.deleteScore(stuId);
		ra.addFlashAttribute("msg", "delSuccess");
		
		return "redirect:/score/list";
	}
	
	
	
}
