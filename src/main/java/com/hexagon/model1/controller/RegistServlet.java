package com.hexagon.model1.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hexagon.model1.pool.PoolManager;

/* 글쓰기 요청을 처리할 서블릿*/
/* JSP도 서블릿이기 때문에 당연히 글쓰기 요청을 받을 수는 있으나, 업무 목적상 디자인이 관려되지 않는다면 굳이 jsp를 쓰게되면
 * 다른 개발자들이 디자인이 포함되어 있는줄 알고 혼동*/
public class RegistServlet extends HttpServlet {
	PoolManager pool = PoolManager.getInstance();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// PoolManager pool = null;
		
		// web 브라우저가 전송한 파라미터 받기
		request.setCharacterEncoding("UTF-8"); // 받아온 한글 등의 파라미터값이 깨지지 않도록 인코딩
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");		
		
		System.out.println(title);
		System.out.println(writer);
		System.out.println(content);
		
		/*
		 * 1. 드라이버 접속
		 * 2. 연결 
		 * 1,2는 안해도됨
		 * 
		 * 이 시점 부터는 오라클 직접 접속하거나, 해제하는 작업은 불필요
		 * PoolManager이 Tomcat의 커넥션풀로부터 Connection을 얻거나 (getConnection())
		 * 돌려보내준다 (release())
		 * */
		Connection con = pool.getConnection(); // 풀로부터 커넥션 한개 빌려오기
		
		//2. 쿼리 수행
		PreparedStatement pstmt=null;
		StringBuilder sb = new StringBuilder(); //StringBuffer는 thread 안전을 위해 안전장치가 있으므로 속도가 느림
		sb.append("insert into board(board_id, title, write, content) values(seq_board.nextval,?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sb.toString()); // 쿼리수행 객체를 생성
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			
			int rowCount = pstmt.executeUpdate(); // 실행 DML이기 때문. insert문의 경우 성공시 반영된 레코드 수는 1개이므로 1이 반환
												// 만일 0이 반환되면 insert 실패
			// 응답정보 만들기
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			StringBuilder tag = new StringBuilder();
			
			tag.append("<script>");
			if(rowCount !=1) {
				tag.append("alert('fail');");
				tag.append("history.back();"); // 브라우저의 뒤로가기 버튼 눌렀을때랑 같음, 즉 이전 히스토리로 화면 전환(글쓰기 실패하면 돌아감)
				//System.out.println("fail");
			}else {
				tag.append("alert('success');");
				//System.out.println("success");
				// 목록으로 보낼 예정
				tag.append("location.href='/board/list.jsp';");
				}
			tag.append("</script>");
			
			out.print(tag.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {	
			pool.release(con,pstmt);
		}
	}
}