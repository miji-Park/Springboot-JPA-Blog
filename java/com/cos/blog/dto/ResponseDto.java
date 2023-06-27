package com.cos.blog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> { //제네릭 걸어줘
	int status; //UserApiController에서 OK가 enum타입이라 int에서 바꿔줌 =>글로벏익셉션 수정하느라 다시 int로
	T data; 

}
