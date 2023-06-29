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
	
	@Transactional(readOnly = true) //셀렉트만 하는거라서
	public Page<Board> 글목록(Pageable pageable){ //페이징 전에는 리스트타입, 페이징하면 페이지 타입으로
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)//셀렉트만 하는거라서
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id) //글 수정하려면 영속화를 먼저해야해
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹이 일어남(영속화돼있는 board 데이터가 달라져서) - 자동 업데이트 됨 .DB쪽으로 flush(커밋) 
	}
}
