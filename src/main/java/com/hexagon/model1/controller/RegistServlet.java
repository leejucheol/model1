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

import com.hexagon.model1.dao.BoardDAO;
import com.hexagon.model1.dto.Board;
import com.hexagon.model1.pool.PoolManager;

/* 글쓰기 요청을 처리할 서블릿*/
/* JSP도 서블릿이기 때문에 당연히 글쓰기 요청을 받을 수는 있으나, 업무 목적상 디자인이 관려되지 않는다면 굳이 jsp를 쓰게되면
 * 다른 개발자들이 디자인이 포함되어 있는줄 알고 혼동*/
public class RegistServlet extends HttpServlet {
	BoardDAO boardDAO = new BoardDAO();

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
		
		// DB에 넣기
		// 파라미터들을 DAO에 전달할때 낱개말고 바구니에 담아서 바구니 자체를 전달(배열보다 훨씬 직관성있음)
		Board board = new Board();
		board.setTitle(title);
		board.setWrite(writer);
		board.setContent(content);
		
		int rowCount = boardDAO.insert(board);
			
		// 아래코드는 디자인과 관련되어 있어 DB에 먼저 넣고 실행
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
	}
}