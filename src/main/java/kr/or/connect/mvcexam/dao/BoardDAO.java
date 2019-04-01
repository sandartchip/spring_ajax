package kr.or.connect.mvcexam.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.or.connect.mvcexam.vo.BoardVO;

public class BoardDAO {  
	
	Connection conn = null; 
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	String driverName = "com.mysql.jdbc.Driver";
 	String dbUrl = "jdbc:mysql://localhost:3306/boarddb2?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
	String dbUser = "root";
	String dbPassword = "root";
	
	public Connection getConnection() { 

		try{  
			Class.forName(driverName);  //driver LOAD! 
			
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);  
		} catch(SQLException es) { 
			es.printStackTrace();
		} catch(ClassNotFoundException ec) {
			ec.printStackTrace();
		} catch(Exception ex) { 
			ex.printStackTrace();
		} 
		return conn;   
	} //END OF DBConnection
	
	public ArrayList<BoardVO> list() { 
		
		ArrayList<BoardVO> vo_list = new ArrayList<BoardVO>();  
		
		try {
			conn = this.getConnection();
			String list_sql = "SELECT * FROM board_table";
			stmt = conn.createStatement(); 
			resultSet = stmt.executeQuery(list_sql);
			
			while(resultSet.next()){ 
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				String regDate = resultSet.getString("regDate");
				String modDate = resultSet.getString("modDate");
				int content_id = resultSet.getInt("content_id"); 
				
				BoardVO data = new BoardVO();
				data.setTitle(title);
				data.setContent(content);
				data.setRegDate(regDate);
				data.setModDate(modDate);
				data.setContent_id(content_id);  
				vo_list.add(data); 
			}
		} catch(SQLException e) {
			
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close(); 
			} catch(Exception e2) {}
		} 
		
		return vo_list;
	} 
	
	public BoardVO contentView(String content_id_str) {
		BoardVO content_vo = new BoardVO();
		String content_sql = "SELECT * FROM board_table WHERE content_id=" + content_id_str;

		try {
			conn = this.getConnection();
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(content_sql);
			
			while(resultSet.next()){ 
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				String regDate = resultSet.getString("regDate");
				String modDate = resultSet.getString("modDate");
				int content_id = resultSet.getInt("content_id");  
				 
				content_vo.setTitle(title);
				content_vo.setContent(content);
				content_vo.setRegDate(regDate);
				content_vo.setModDate(modDate);
				content_vo.setContent_id(content_id);   
			} 

		} catch(SQLException e) {
			
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close(); 
			} catch(Exception e2) {}
		} 
		return content_vo;
	}
	
	public void write(String title, String content) {
		PreparedStatement pstmt = null;
		
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dayTime.format(new Date(time));
		String modDate, regDate;
		
		regDate=modDate=str; 
		//날짜 등록. 
		
		String insert_sql = "INSERT INTO board_table (title, content, regDate, modDate) values(?,?,?,?)";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(insert_sql);
			//sql 쿼리
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, regDate);
			pstmt.setString(4, modDate);
			pstmt.executeUpdate();   
		} catch (SQLException e1) { 
			e1.printStackTrace();
		}
	}
	public void delete(String content_id) {
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			String query = "delete from board_table where content_id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,  Integer.parseInt(content_id));
			int rn = pstmt.executeUpdate();
			
			/*  rearrange     */
			
			Statement stmt;
			stmt = conn.createStatement();
			
			String rearrange_idx_sql = 
					"ALTER TABLE board_table drop content_id;"; // IDX 재조정 위해 컬럼삭제
			stmt.executeUpdate(rearrange_idx_sql);
				
			rearrange_idx_sql ="ALTER TABLE board_table ADD content_id int primary key auto_increment";
			//FIRST 구문은 나중에
			
			stmt.executeUpdate(rearrange_idx_sql);
		  	
		} catch (SQLException e) {
			
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(stmt != null) stmt.close();
				if(pstmt!= null) pstmt.close();
				if(conn != null) conn.close(); 
			} catch(Exception e2) {}
		}
	}
}