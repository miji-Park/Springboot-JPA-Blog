package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repositoty.BoardRepository;
import com.cos.blog.repositoty.UserRepository;


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

}
