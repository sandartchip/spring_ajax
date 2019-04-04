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
import kr.or.connect.mvcexam.vo.Criteria;

public class BoardDAO {  
	
	Connection conn = null; 
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	String driverName = "com.mysql.jdbc.Driver";
 	String dbUrl = "jdbc:mysql://localhost:3306/boarddb2?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
	String dbUser = "root";
	String dbPassword = "root";
	int totalCount=0;

	
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
	
	public ArrayList<BoardVO> list(Criteria page_info) throws SQLException { 
		// 표시할 페이지의 번호, 출력할 페이지의 갯수
	 
		ArrayList<BoardVO> vo_list = new ArrayList<BoardVO>();  
		int pagePerNum = page_info.getPagePerNum(); //1페이지 당 게시물 갯수
		int curPageNum = page_info.getPage(); //현재 페이지 번호
		
		//pagePerNum과 curPagenum 이용해서 계산.
		
		int start_content_num;
		int end_content_num;
		ResultSet resultSet = null;
		int totalCount;
		
		totalCount = this.totalCount();
		
		start_content_num = (curPageNum-1) * pagePerNum;
		
		
		// 1페이지 : 0행~9
		// 2페이지: 10~19
		
		// 이거 아님. 게시글은 1번부터가 아니라 표시되는건 내림차순임.
		// 단지 0~21을 21~0으로 순서만 바꾼 것
		// 전체 갯수에서 빼주면 됨.
		
	  	System.out.println("시작 컨텐츠번호: "+start_content_num);
//	  	System.out.println("끝 컨텐츠 번호: "+end_content_num);
	  	System.out.println("======================================");
	    
		try {
			conn = this.getConnection();
			//String list_sql = "SELECT * FROM board_table"; //원래 SQL
			
			// 전체 게시물 받아오는걸 -> 현재 페이지의 시작 게시물 번호 to 끝 게시물 번호까지
			String page_list_sql = 
					"SELECT * FROM board_table ORDER BY content_id DESC LIMIT ?, ?"; 
 
			PreparedStatement page_pstmt=null;
			
			// 리스트로 몇 개 받아올지만 .
			page_pstmt = conn.prepareStatement(page_list_sql);
			page_pstmt.setInt(1, start_content_num);  // 현재 페이지의 시작 게시물 번호
			page_pstmt.setInt(2, pagePerNum);    // 현재 페이지의  끝 게시물 번호까지
			
			resultSet = page_pstmt.executeQuery(); //해당 prepared statement에 쿼리문 실행. 
			
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
			
			System.out.println("==========현재 페이지 ArrayList 조회==============");
			for(BoardVO board: vo_list) {
				System.out.println(vo_list);
			}
			
		} catch(Exception e) {
		}	finally {
				if(resultSet != null) resultSet.close();
				if(pstmt!= null) pstmt.close();
				if(conn != null) conn.close();
		} //end of finally
		return vo_list;
	} // end of list
	
	public int totalCount() throws SQLException {
		Statement total_stmt=  null;
		ResultSet total_result=null;
		
		try {
			conn = this.getConnection();
			//String list_sql = "SELECT * FROM board_table"; //원래 SQL
			/* TOTAL COUNT */
			System.out.println("최종 페이지 계산!!");
			
			String total_count_sql = "SELECT COUNT(*) FROM board_table";
			total_stmt = conn.createStatement();//Static한 SQL문 처리하는 Object
			total_result=total_stmt.executeQuery(total_count_sql);
			
			while(total_result.next()) {
				//System.out.println("ddd");
				totalCount = total_result.getInt(1);
			}
		}
		catch(Exception e) {}	
		finally {
				if(total_result != null) total_result.close();
				if(total_stmt   != null) total_stmt.close();
				if(conn != null) conn.close();
		} //end of finally
		System.out.println("total count = "+ totalCount);
		return totalCount;
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
			} //리퀘스트 파라미터에

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
	
	public void modify(String content_id, String title, String content) {
		
		/* 현재 시간 구하기 */
		String modDate;
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dayTime.format(new Date(time));
		modDate = str;
		/* 현재시간 업데이트 */
		
		String modify_sql =
				"UPDATE board_table SET TITLE= ?, CONTENT=?, modDate=?  WHERE content_id=?";
		
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(modify_sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, modDate);
			pstmt.setInt(4, Integer.parseInt(content_id));
			int rn = pstmt.executeUpdate();
		} catch(SQLException e) {
			
		} finally {
			try {
				if(resultSet != null) resultSet.close();
				if(pstmt!= null) pstmt.close();
				if(conn != null) conn.close(); 
			} catch(Exception e2) {}
		}
	}
	public void delete(String content_id) {
		PreparedStatement pstmt = null;
		String query = "delete from board_table where content_id = ?";
		
		try {
			conn = this.getConnection();
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