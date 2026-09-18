package com.hexagon.model1.dto;

/*
 * 이 클래스는 로직 작성용이 아니라, 오직 데이터를 모아서 전달하기 위한 용도임
 * 객체지향에서는 애플리케이션에서 다루고자 하는 데이터를 보안상 은닉화 (capsulation)
 *  
 * */
public class Board {
	private Long boardId; // int 보다 더 큰 자료형인 long으로 선언해야 오래된 게시물의 숫자를 표현할 수 있으므로 규모가 커야함
							// 소문자 long으로 선언하면 기본자료형임, 게시물이 없을때 null을 표현할 수 없음
							// db 의 컬럼명이 단어의 조합일 경우 _를 흔히 사용,java 분야에서는 _쓰지 않고 camel기법을 사용
							// 이 기법을 준수하면 추후 사용하게될 DB 분야의 framework인 JPA, Mybatis framework를 사용할때 좋다
	private String title;
	private String writer;
	private String content;
	private String createdAt;
	private Long hit;
	
	public String getTitle() {
		return title;
	}
	public String getTitle(String title) {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getWrite() {
		return writer;
	}
	public Long getBoardId() {
		return boardId;
	}
	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public void setWrite(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
