package com.spring.myweb.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.command.UserVO;
import com.spring.myweb.user.mapper.IUserMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService {
	
	@Autowired
	private IUserMapper mapper;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Override
	public int idCheck(String id) {
		return mapper.idCheck(id);
	}

	@Override
	public void join(UserVO vo) {
		
		// 회원 비밀번호를 암호화 인코딩
		
		
		log.info("암호화 하기 전 비번: " + vo.getUserPw());
		
		// 비밀번호를 암호화 해서 vo 객체에 다시 저장하기
		String securePw = encoder.encode(vo.getUserPw());
		log.info("암호화 후에 비번: " + securePw);
		vo.setUserPw(securePw);
		
		mapper.join(vo);
	}

	@Override
	public UserVO login(String id, String pw) {
		// id 정보를 기반으로 회원의 정보를 조회
		UserVO vo = getInfo(id);
		if(vo != null) {
			String dbPw = vo.getUserPw(); // DB에서 가져온 암호화 된 비밀번호.
			if(encoder.matches(pw, dbPw)) { // matches 로 전 pw와 암호화 된 pw를 비교해준다
				return vo;
			}
		}
		
		return null;
		
	}
	

	@Override
	public UserVO getInfo(String id) {
		return mapper.getInfo(id);
	}

	@Override
	public void updateUser(UserVO vo) {
		// TODO Auto-generated method stub

	}

}
