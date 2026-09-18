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

// 글 수정 요청하는 서블릿
public class UpdateServlet extends HttpServlet {
	PoolManager pool = PoolManager.getInstance();
	
	@Override
	// 글 수정 요청은 글쓰기와 마찬가지로 파라미터의 값이 너무 거대함( 글내용의 경우 편집기 사용으로 인한 태그까지도 포함
	// POST 방식으로 요청이 들어오기 때문 doPost()로 요청을 처리하자 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// update board set title='title2', writer='updated writer', content='hi -> hello' where board_id=내가 본글 board_id; 
		String title = request.getParameter("title");
		String write = request.getParameter("write");
		String content = request.getParameter("content");
		int board_id = Integer.parseInt(request.getParameter("board_id")); // hidden으로부터 온 파라미터
		
		String sql="update board set title=?, write=?, content=? where board_id=?";
		
		// DML (Connection, PreparedStatment
		Connection con=pool.getConnection();
		PreparedStatement pstmt=null;
		
		
		try {
			pstmt=con.prepareStatement(sql); // 쿼리 수행 객체 얻기
			pstmt.setString(1, title); // 쿼리문 첫번째
			pstmt.setString(2, write); // 두
			pstmt.setString(3, content); // 세
			pstmt.setInt(4, board_id); // 넷 은 숫자라 setInt
			
			// DML 실행
			int rowCount = pstmt.executeUpdate(); // 반영된 레코드 수를 반환 0 나오면 실패
			
			response.setContentType("text/html, charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.print("<script>");
			if(rowCount==0){
				out.print("alert('update fail');");
				out.print("history.back();");
			}else {
				out.print("alert('update success');");
				out.print("location.href='/board/content.jsp?x=" + board_id + "';"); // content.jsp는 pk값이 x로 지정되어있어서 변수명 x로 변경
			}
			
			out.print("</script>");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt);
		}
		
	}
}
