package com.spring.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.db.model.ScoreVO;
import com.spring.db.repository.IScoreDAO;
import com.spring.db.repository.IScoreMapper;

@Service
public class ScoreService implements IScoreService {

	@Autowired
	private IScoreMapper mapper;
	
	@Override
	public void insertScoSre(ScoreVO vo) {

		vo.calcData();
		System.out.println("service: " + vo);
		mapper.insertScoSre(vo);
		
	}

	@Override
	public List<ScoreVO> selectAllScores() {
		return mapper.selectAllScores();
	}

	@Override
	public void deleteScore(int num) {

		mapper.deleteScore(num);
	}

	@Override
	public ScoreVO SelectOne(int num) {
		return mapper.SelectOne(num);
	}

}
