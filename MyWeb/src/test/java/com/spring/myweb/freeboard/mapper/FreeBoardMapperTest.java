package com.spring.myweb.freeboard.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.util.PageVO;

@ExtendWith(SpringExtension.class) // 테스트 환경을 만들어 주는 junit5 객체 로딩
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/db-config.xml"
		
		
})

public class FreeBoardMapperTest {
	
	@Autowired
	private IFreeBoardMapper mapper;
	
	// 단위 테스트 (unit test) - 가장 작은 단위의 테스트
	// 테스트 시나리오
	// - 단언 (Assertion) 기법
	
	@Test
	@DisplayName("Mapper 계층의 regist를 호출하면서 "
			+ "FreeBoardVO를 전달하면 데이터가 INSERT 된다.")
	void registTest() {
		// given - when - then 패턴을 따릅니다 (생략 가능)
		
		// given : 테스트를 위해 주어질 데이터 (ex: parameter)
		for(int i=1; i<=10; i++) {
			FreeBoardVO vo = new FreeBoardVO();
			vo.setTitle("테스트 용석" + i);
			vo.setWriter("park1234");
			vo.setContent("테스트 용석이" + i);
			// when : 테스트 실제 상황 
			mapper.regist(vo);
		}
		
		
		// then: 테스트 결과를 확인.
		
		
	}
	
	@Test
	@DisplayName("사용자가 원하는 페이지 번호에 맞는 글 목록을 불러 올 것이고 게시물의 개수는 사용자가 원하는 만큼 개수를 가진다")
	void getListTest() {
		
		PageVO vo = new PageVO();
		vo.setPageNum(7);
		
		List<FreeBoardVO> list = mapper.getList(vo);
		
		list.forEach(article -> System.out.println(vo));
		
		assertEquals(vo.getCpp(), list.size());
	}
	
	@Test
	@DisplayName("글 번호가 2번인 글을 조회하면 글쓴이는 abc1234일 것이고 글 내용은 메롱메롱 일 것이다.")
	
	void getContentTest() {
		
		// given
		int bno = 2;
		
		// when 
		FreeBoardVO vo = mapper.getContent(bno);
		
		// then
		assertEquals("abc1234", vo.getWriter());
		assertEquals("메롱메롱", vo.getContent());
//		assertTrue(vo.getContent().equals("메롱메롱"));
		
//		assertNull(vo);
		
	}
	
	@Test
	@DisplayName("글 번호가 1번인 글의 제목과 내용을 수정 후 다시 조회했을 때 "
			+ "제목이 수정한 제목으로 바귀었음을 단언한다.")
	
	void updateTest() {
		
		FreeBoardVO vo = new FreeBoardVO();
		vo.setBno(1);
		vo.setTitle("이름 바꿔버리기");
		vo.setContent("내용도 바꿔버리기");
		
		mapper.update(vo);
		
		assertEquals(vo.getTitle(), mapper.getContent(vo.getBno()).getTitle());
		
		
	}
	
	@Test
	@DisplayName("글 번호가 2번인 글을 삭제한 후에 리스트를 전체 조회했을 때 "
			+ "글의 개수는 1개일 것이고, "
			+ "2번 글을 조회했을 떄 null이 반환되어야 한다.")
	
	void deleteTest() {
		
		int bno = 2;
		
		mapper.delete(bno);
		
//		assertEquals(1, mapper.getList().size());
		assertNull(mapper.getContent(bno));
	}
	
}
