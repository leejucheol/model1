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

// 글 삭제 요청을 처리하는 서블릿, 글 삭제는 데베에서부터 레코드 삭제, 디자인이 관여 되지 않음
// 따라서 굳이 jsp를 사용할 필요가 없음
public class DeleteServlet extends HttpServlet {
	PoolManager pool = PoolManager.getInstance(); // 싱글턴 패턴에 의한 인스턴스 얻기
	// delete board where board_id=내가본글pk
	// 파라미터가 보안상 중요하지도 않고 즉 url에 노출되어도 상관없고, 그 데이터량도 크지 않으므로
	// get 방식으로도 처리할 수 있다..(당연히 post도 가능)
	
	@Override
	// 클라이언트의 요청 방식이 GET 방식일때 호출되는 섭ㄹ릿의 메서드, 여기에 코드를 작성하는 것은 jsp의 스크립틀릿에 작성하는 것과 동일 (<%%> 영역)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int board_id=Integer.parseInt(request.getParameter("board_id"));
		
		String sql="delete board where board_id=" + board_id;
		
		// 쿼리문 출력
		response.setContentType("text/html; charset=UTF-8"); //<%@ page contentType="text/html; charset=UTF-8"%> 과 동일
		PrintWriter out = response.getWriter(); // 응답 객체에 들어있는 문자기반 출력 스트림을 꺼내기(개발자가 여기에 문자열을 보관해놓으면
												// 고양이가 추후 응답 정보로 사용하기 때문
		
		out.print(sql);
		/* 원래 JDBC 연동 업무는 아래의 4단계로 진행을 해야하지만, 우리의 경우 1,2단계는 Tomcat이 커넥션풀링을 이용하게 해줌
		 * 현재 우리 애플리케이션을 톰켓이 작업해놓은 Connection Pool, JNDI를 이용하여 검색한 후 DataSource를 통해 톰셋이 마련해놓은 커넥션풀에
		 * 접근하고 잇음 (by PoolManager라는 우리가 정의한 클래스 - singleTon 패턴으로 정의함
		 * 1. 드라이버 로드
		 * 2. 접속
		 * 3. 쿼리수행
		 * 4. 접속헤제
		 * */
		
		Connection con = pool.getConnection(); // 커넥션풀로부터 1개의 Connection 빌려오기
		PreparedStatement pstmt = null;
		
		try {
			pstmt =con.prepareStatement(sql);
			int rowCount = pstmt.executeUpdate(); // DML(insert,update, delete)의 경우 사용되는 메서드
			
			out.print("<script>");
			if(rowCount==0) {
				out.print("alert('fail');");
				out.print("history.back();");
			} else {
				out.print("alert('success');");
				out.print("location.href='/board/list.jsp'");
			}
			out.print("</script>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.release(con, pstmt); // 자원해제 connection은 풀로 돌어가고 pstmt는 종료됨
		}
	}
}
