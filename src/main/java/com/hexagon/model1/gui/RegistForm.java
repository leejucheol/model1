package com.hexagon.model1.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/*
web 기반이 아닌 java SE 기반의 디자인폼으로 게시판 글쓰기를 제작해보기!! 
*/
public class RegistForm extends JFrame{
    JTextField t_title;
    JTextField t_writer;
    JTextArea t_content;
    JButton bt;

    public RegistForm() {
        //부품 생성하기
        t_title = new JTextField(27);
        t_writer = new JTextField(27);
        t_content = new JTextArea();
        bt = new JButton("글등록");

        //스타일 예쁘게 꾸미기 
        t_content.setPreferredSize(new Dimension(290, 200));

        //조립하기
        setLayout(new FlowLayout()); //조립전 레이아웃 적용하기 
        add(t_title);
        add(t_writer);
        add(t_content);
        add(bt);
        
        // link button and listener
        bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				regist();
			}
		});

        //윈도우 설정 
        setSize(300,350);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void regist() {
		System.out.println("Did you press?");
		
		 /* 
		  * 1. 드라이버 로드
		 * 2. 접속
		 * 3. 쿼리수행
		 * 4. 접속헤제
		 * */
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "web", "1234");
			if(con==null) {
				System.out.println("Access fail");
			} else {
				System.out.println("Access success");
			}
			
			pstmt=con.prepareStatement("insert into board(board_id, title, writer, content) values(seq_board.nextval,?,?,?)");

			pstmt.setString(1, t_title.getText());
			pstmt.setString(2, t_writer.getText());
			pstmt.setString(3, t_content.getText());
			
			int rowCoumnt = pstmt.executeUpdate(); // run query
			
			if(rowCoumnt==0) {
				System.out.println("fail regist");
			} else {
				JOptionPane.showMessageDialog(this, "success regist");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con != null)
				try {
					if(pstmt!=null)pstmt.close();
					if(con!=null)con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

    public static void main(String[] args) {
        new RegistForm();
    }

}