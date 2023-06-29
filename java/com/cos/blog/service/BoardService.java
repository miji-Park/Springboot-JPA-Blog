package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repositoty.BoardRepository;


@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	
	@Transactional
	public void 글쓰기(Board board, User user) { //title,content 2가지 받는다
		board.setCount(0); //조회수는 강제로 넣어줄게
		board.setUser(user);
		boardRepository.save(board);
	}
	
	public Page<Board> 글목록(Pageable pageable){ //페이징 전에는 리스트타입, 페이징하면 페이지 타입으로
		return boardRepository.findAll(pageable);
	}

}
