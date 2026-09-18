package com.hexagon.model1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexagon.model1.dto.Board;
import com.hexagon.model1.pool.PoolManager;

/*
DAO 란?
1) Data Access Object의 약어이다
2) 오직 데이터베이스 관련한 작업(C=insert R=select U=update D=delete)만을 전담하는 객체
3) 중립적이어야 모든 플랫폼에서 재사용 가능성이 높다 ..
4) 애플리케이션 설계 분야의 용어이기 때문에 javaEE 분야에 국한된 개념이 아니다 !!
 * */
public class BoardDAO {
	PoolManager pool = PoolManager.getInstance();
	
	// insert method
	// 매개변수로 배열을 사용해도 되지만, 각 데이터 접근 시 인덱스 0,1,2
	public int insert(Board board) {
		Connection con=null;
		PreparedStatement pstmt=null;
		
		con = pool.getConnection();
		String sql="insert into board (board_id, title, write, content) values(seq_board.nextval,?,?,?)";
		
		int rowCount=0; // return 하기 위한 변수
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWrite());
			pstmt.setString(3, board.getContent());
			rowCount = pstmt.executeUpdate(); // 쿼리 실행
			
			/* DAO 는 데이터베이스 관련된 업무만 집중하는 공통 코드이다 ....
			* 따라서 이 클래스에서는 결과를 브라우저에 맞게 보여줄지, Swing 에 맞게 보여줄지 여부를 고민하지 않는다 .. 또한 해서도 안된다 .!!!!
			* 해결책) 그냥 결과를 반환하면 된다 .. 반환받은 자가 웹이면 alert()으로 보여주고, Swing 이면 다이얼로그로 보여주면 된다 ...
			* */
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.release(con, pstmt);
		}
		
		return rowCount;
	}
	
	// select specific one thing method
	public void select() {
		String sql="select * from board where board_id=?";
	}
	
	// select all method
	public List selectAll() {
		Connection con=pool.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null; //select문 실행 후 그 결과인 표를 받는 객체
		List list = new ArrayList(); // 비어있는 리스트 생성
									// 리스트는 사실 배열과 거의 같음 (차이점은 1. 오직 객체만 담는다 2. 크기가 자유롭다)
									// 유연한 객체전용 배열이라고 생각
		
		con=pool.getConnection();
		String sql="select * from board order by board_id desc"; 
		
		try {
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // select 실행 및 표반환
			// 이 DAO는 오직 DB 관련 업무만 담당, rs를 이용한 표 만들기는 여기서 하지 않기
			// rs 는 곧 finally에서 죽을 예정이라 rs와 거의 흡사한 형태의 java객체로 옮겨담자
			// 1) rs 표의 순서있는 집합은 java.util의 List로 모방
			// 2) rs의 레코드 한건은 Board 클래스의 인스턴스 1개로 모방 
			
			while(rs.next()) { // 레코드 수만큼 반복
				Board board = new Board(); // 비어있는 DTO한개 생성
				board.setBoardId(rs.getLong("board_id"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setCreatedAt(rs.getString("created_at")); // 
				board.setHit(rs.getLong("hit")); // 조회수 넣기
				
				list.add(board); // list에 밀어넣기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.release(con, pstmt);
		}
		return list;
	}

	// update method
	public void update() {
		String sql="update board set title=?, writer=?, content=? where board_id=?";
	}
	
	// delete method
	public void delett() {
		String sql="delete board where board_id=?";
	}

}
