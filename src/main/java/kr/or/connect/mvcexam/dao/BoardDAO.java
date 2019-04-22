package kr.or.connect.mvcexam.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

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
	 
	public int totalCount() throws SQLException { //list total count.
		
		ResultSet total_result=null;
		Statement total_stmt = null;
		try {
			conn = this.getConnection();
			//String list_sql = "SELECT * FROM board_table"; //원래 SQL
			// TOTAL COUNT 
			System.out.println("최종 페이지 계산!!");
			
			total_stmt = null;
			String total_count_sql = "SELECT COUNT(*) FROM board_table";
			total_stmt = conn.createStatement();//Static한 SQL문 처리하는 Object
			total_result=total_stmt.executeQuery(total_count_sql);
			
			while(total_result.next()) {
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
	public ArrayList<BoardVO> search_page
	
	(String keyword, String search_type, Date start_date, Date end_date, Criteria page_info) throws SQLException{
		Connection conn = null; 
		PreparedStatement page_pstmt=null; //sql쿼리문이 아니라 DB랑 쿼리문 연결시켜준 Object.
		ArrayList<BoardVO> vo_list= new ArrayList<BoardVO>();

		System.out.println("------------날짜 and date 검색으로 진입-----------");
		
		int pagePerNum = page_info.getPagePerNum(); //1페이지 당 게시물 갯수
		int curPageNum = page_info.getPage(); //현재 페이지 번호
		int start_content_num; 
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String page_list_sql;
		ResultSet resultSet=null;
		
		
		start_content_num = (curPageNum-1) * pagePerNum; 
		// 이게 일반페이지이 일 땐 이 공식인데
		// ajax방식일 땐 무조건 1이어야..
		
		page_list_sql = "SELECT * FROM board_table WHERE regDate between DATE(?) and DATE(?)+1 "
				+ "and title LIKE ? "
				+ "ORDER BY content_id DESC LIMIT ?, ?";
		//  붙여야 한다. 
		 
		System.out.println( start_date+" " + end_date+" " + keyword+" "+ start_content_num + " " + pagePerNum);
		
		try {
			conn = this.getConnection();
			page_pstmt = conn.prepareStatement(page_list_sql);
			page_pstmt.setDate(1, start_date);// 물 번호
			page_pstmt.setDate(2, end_date);    // 현재 페이지의  끝 게시물 번호까지 
			page_pstmt.setString(3, "%"+keyword+"%");
			page_pstmt.setInt(4,  start_content_num);
			page_pstmt.setInt(5,  pagePerNum);
			
			resultSet = page_pstmt.executeQuery(); //해당 prepared statement에 쿼리문 실행. 
			
			while(resultSet.next()){ //DB에서 데이터 가져오기.
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				String regDate = resultSet.getString("regDate");
				String modDate = resultSet.getString("modDate");
				String writer  = resultSet.getString("writer");
				
				int content_id = resultSet.getInt("content_id"); 
				
				BoardVO data = new BoardVO();
				
				data.setWriter(writer);
				data.setTitle(title);
				data.setContent(content);
				data.setRegDate(regDate);
				data.setModDate(modDate);
				data.setContent_id(content_id);  
				
				vo_list.add(data);
				System.out.println("제목:"+title+"내용:"+content+"작성자:"+writer);
			} //end of while
		} 		
		catch(Exception e) {} 
		finally {
			if(conn != null) conn.close();
			if(resultSet != null) resultSet.close();
			if(page_pstmt   != null) page_pstmt.close();
		} //end of finally
		return vo_list;
	}
	public ArrayList<BoardVO> search_page(Date start_date, Date end_date, Criteria page_info) throws ParseException, SQLException{
		Connection conn = null; 
		PreparedStatement page_pstmt=null; //sql쿼리문이 아니라 DB랑 쿼리문 연결시켜준 Object.
		ArrayList<BoardVO> vo_list= new ArrayList<BoardVO>();

		System.out.println("------------날짜 검색으로 진입-----------");
		
		int pagePerNum = page_info.getPagePerNum(); //1페이지 당 게시물 갯수
		int curPageNum = page_info.getPage(); //현재 페이지 번호
		int start_content_num; 
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String page_list_sql;
		ResultSet resultSet=null;
		
		
		start_content_num = (curPageNum-1) * pagePerNum; 
		//start_content_num=1;

		page_list_sql = 		
				"SELECT * FROM board_table "
				+ "WHERE regDate between DATE(?) and DATE(?)+1 "+
				"ORDER BY content_id DESC LIMIT ?, ?";
		//  붙여야 한다. 
		 
		try {
			conn = this.getConnection();
			page_pstmt = conn.prepareStatement(page_list_sql);
			page_pstmt.setDate(1, start_date);// 물 번호
			page_pstmt.setDate(2, end_date);    // 현재 페이지의  끝 게시물 번호까지 
			page_pstmt.setInt(3,  start_content_num);
			page_pstmt.setInt(4,  pagePerNum);
			
			resultSet = page_pstmt.executeQuery(); //해당 prepared statement에 쿼리문 실행. 
			
			while(resultSet.next()){ //DB에서 데이터 가져오기.
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				String regDate = resultSet.getString("regDate");
				String modDate = resultSet.getString("modDate");
				String writer  = resultSet.getString("writer");
				
				int content_id = resultSet.getInt("content_id"); 
				
				BoardVO data = new BoardVO();
				
				data.setWriter(writer);
				data.setTitle(title);
				data.setContent(content);
				data.setRegDate(regDate);
				data.setModDate(modDate);
				data.setContent_id(content_id);  
				
				vo_list.add(data);
				System.out.println("제목:"+title+"내용:"+content+"작성자:"+writer);
			} //end of while
		} 		
		catch(Exception e) {} 
		finally {
			if(conn != null) conn.close();
			if(resultSet != null) resultSet.close();
			if(page_pstmt   != null) page_pstmt.close();
		} //end of finally
		return vo_list;
	}
	public ArrayList<BoardVO> search_page(String search_type, String keyword, Criteria page_info) throws SQLException { 
	
		// 검색할 키워드를 파라미터로 받아 온다.
		
		// 검색 결과 게시물을 -> ArrayList로 만들어서 -> 새로운 vo_list에 -> 컨텐츠 리스트를 arrayList로 만들어서 리턴.
	
		ResultSet search_result=null;
		PreparedStatement page_pstmt=null; //sql쿼리문이 아니라 DB랑 쿼리문 연결시켜준 Object.
		ArrayList<BoardVO> vo_list= new ArrayList<BoardVO>();
		
		int pagePerNum = page_info.getPagePerNum(); //1페이지 당 게시물 갯수
		int curPageNum = page_info.getPage(); //현재 페이지 번호
		int start_content_num;    
		// 하지만 검색에 따라 고려되어야 할 듯.
		
		start_content_num = (curPageNum-1) * pagePerNum;
		//start_content_num=1;
		
		
		try {
			conn = this.getConnection();
			//String list_sql = "SELECT * FROM board_table"; //원래 SQL
			/* TOTAL COUNT */
			String page_list_sql="";
			PreparedStatement list_page_pstmt=null;
			
			if(keyword.length()==0) {
		
				System.out.println("--------검색 키워드 X, 전체 검색----------");
				
				page_list_sql = 
						"SELECT * FROM board_table ORDER BY content_id DESC LIMIT ?, ?";
				list_page_pstmt = conn.prepareStatement(page_list_sql);
				list_page_pstmt.setInt(1, start_content_num);  // 현재 페이지의 시작 게시물 번호
				list_page_pstmt.setInt(2, pagePerNum);    // 현재 페이지의  끝 게시물 번호까지
				
				//키워드 없는 경우 
 
				resultSet = list_page_pstmt.executeQuery(); //해당 prepared statement에 쿼리문 실행. 
				
				while(resultSet.next()){ //DB에서 데이터 가져오기.
					String title = resultSet.getString("title");
					String content = resultSet.getString("content");
					String regDate = resultSet.getString("regDate");
					String modDate = resultSet.getString("modDate");
					String writer  = resultSet.getString("writer");
					int content_id = resultSet.getInt("content_id"); 
					
					BoardVO data = new BoardVO();
					data.setTitle(title);
					data.setContent(content);
					data.setRegDate(regDate);
					data.setModDate(modDate);
					data.setContent_id(content_id);
					data.setWriter(writer);
					vo_list.add(data);
				} //end of while
			}// end of 검색키워드 업는 경우.
			else if(keyword.length()>0){
				System.out.println("------검색 키워드 있음 !!--------------"+keyword);

				String search_sql=""; 

				if(search_type.equals("content")) {
					search_sql = "SELECT * FROM board_table WHERE content LIKE ? ORDER BY content_id DESC LIMIT ?, ? "; 
				}
				else if(search_type.equals("title")) {
					search_sql = "SELECT * FROM board_table WHERE title LIKE ? ORDER BY content_id DESC LIMIT ?, ? ";  
				}
				//타입에 따른 sql 쿼리먼을 statement에 셋팅.
				
 				page_pstmt = conn.prepareStatement(search_sql); 
				if(search_type.equals("content") || search_type.equals("title")) {
					page_pstmt.setString(1, "%"+keyword+"%");
					page_pstmt.setInt(2, start_content_num);  // 현재 페이지의 시작 게시물 번호
					page_pstmt.setInt(3, pagePerNum);    // 현재 페이지의  끝 게시물 번호까지
					search_result = page_pstmt.executeQuery();
				}
				while(search_result.next()) {
					
					String title = search_result.getString("title");
					String content = search_result.getString("content");
					String regDate = search_result.getString("regDate");
					String modDate = search_result.getString("modDate");
					String writer  = search_result.getString("writer");
					int content_id = search_result.getInt("content_id");  
					
					BoardVO data = new BoardVO();
					
					data.setWriter(writer);
					data.setTitle(title);
					data.setContent(content);
					data.setRegDate(regDate);
					data.setModDate(modDate);
					data.setContent_id(content_id);  
					vo_list.add(data);
					
					System.out.println("검색후 찾은 데이터 = "+data.getTitle());
				}
				
			}  
			
			// 제목에 ~를 포함하면서
			// 용연님 게시판 검색 쿼리 처리 때문에 그러는데욥  제목에 "we"를 포함시키는 것 중에 3~6번째 이런 식으로 쿼리문을 주고 싶은데.
		}
		catch(Exception e) {} 
		finally {
			
			if(search_result != null) search_result.close();
			if(page_pstmt   != null) page_pstmt.close();
			if(conn != null) conn.close();
		} //end of finally
		return vo_list;
	}
	public int search_totalCount(Date startDate, Date endDate, String keyword) throws SQLException {
		//String, date 정보 둘 다 있는 경우.
		PreparedStatement page_pstmt=null; //sql쿼리문이 아니라 DB랑 쿼리문 연결시켜준 Object.
		ResultSet search_result=null; 
		String search_sql="";
		int row_count=0;
		
		System.out.println(" search -date & keyword-");
		try {
			conn = this.getConnection();
			/* TOTAL COUNT */
			System.out.println("---검색!!---"+startDate + " " + endDate);

			search_sql = "SELECT COUNT(*) FROM board_table "
					+ "WHERE regDate between DATE(?) and DATE(?)+1"
					+ "and title LIKE ? ";
			
			page_pstmt = conn.prepareStatement(search_sql);
			page_pstmt.setDate(1, startDate);
			page_pstmt.setDate(2, endDate);
			page_pstmt.setString(3, "%"+keyword+"%"); 

			search_result = page_pstmt.executeQuery(); 
			
			if(search_result.next()) 
				row_count=search_result.getInt(1); 
		}
		catch(Exception e) {} 
		finally {
			if(conn != null) conn.close();
			if(search_result != null) search_result.close(); 
			if(page_pstmt   != null) page_pstmt.close();
		} //end of finally
		return row_count;
	}
	public int search_totalCount(Date startDate, Date endDate) throws SQLException {
		PreparedStatement page_pstmt=null; //sql쿼리문이 아니라 DB랑 쿼리문 연결시켜준 Object.
		ResultSet search_result=null; 
		
		int row_count=0;
		String search_sql="";
		
		try {
			conn = this.getConnection();
			/* TOTAL COUNT */
			System.out.println("---검색!!---"+startDate + " " + endDate);

			search_sql = "SELECT COUNT(*) FROM board_table WHERE regDate between DATE(?) and DATE(?)+1";
			page_pstmt = conn.prepareStatement(search_sql);
			page_pstmt.setDate(1, startDate);
			page_pstmt.setDate(2, endDate);

			search_result = page_pstmt.executeQuery(); 
			
			if(search_result.next()) 
				row_count=search_result.getInt(1); 
		}
		catch(Exception e) {} 
		finally {
			if(conn != null) conn.close();
			if(search_result != null) search_result.close(); 
			if(page_pstmt   != null) page_pstmt.close();
		} //end of finally
		return row_count;
	}
	public int search_totalCount(String keyword) throws SQLException { 
		/* 검색된 결과 쿼리의 갯수를 리턴. */ 
		PreparedStatement page_pstmt=null; //sql쿼리문이 아니라 DB랑 쿼리문 연결시켜준 Object.
		ResultSet search_result=null; 

		int row_count=0;
		String search_sql="";
		
		try {
			conn = this.getConnection();
			/* TOTAL COUNT */
			System.out.println("---검색!!---"+keyword);
	
			search_sql = "SELECT COUNT(*) FROM board_table WHERE title LIKE ?";
			page_pstmt = conn.prepareStatement(search_sql);
			page_pstmt.setString(1, "%"+keyword+"%");
			search_result = page_pstmt.executeQuery(); 
			
			if(search_result.next()) 
				row_count=search_result.getInt(1); 
		}
		catch(Exception e) {} 
		finally {
			if(conn != null) conn.close();
			if(search_result != null) search_result.close(); 
			if(page_pstmt   != null) page_pstmt.close();
		} //end of finally
		return row_count;
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
				String writer  = resultSet.getString("writer");
				int content_id = resultSet.getInt("content_id");  
				 
				content_vo.setTitle(title);
				content_vo.setContent(content);
				content_vo.setRegDate(regDate);
				content_vo.setModDate(modDate);
				content_vo.setWriter(writer);
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
	
	public void file_write(MultipartFile file) {

		int cur_content_idx=0;
		
		conn = this.getConnection(); 
		String file_name = file.getOriginalFilename();
		
		
		try {
			cur_content_idx = this.totalCount();
			// 현재  전체 데이터 갯수 = insert 할 board_idx 이다.
			//1번 2번 3번까지 게시글 입력->3번에 파일 넣어야
			// 파일이 추가 될 게시글 index = 방금 게시글 입력한 게시글 index = 게시글의 갯수
		} catch (SQLException e1) { 
		} //아까 등록한 게시물의 index 들고 오기.
		try {
			String file_upload_sql = "INSERT INTO fileTable (board_idx, file_name) values(?,?)";
			pstmt = conn.prepareStatement(file_upload_sql);
			
			pstmt.setInt(1, cur_content_idx);
			pstmt.setString(2, file_name);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
		}
	}
	
	public void text_write(String title, String content, String writer) {
		PreparedStatement pstmt = null;
		
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dayTime.format(new Date(time));
		String modDate, regDate;
		
		regDate=modDate=str; 
		//날짜 등록. 
				
		conn = this.getConnection(); 
		
		try {
			String insert_sql = "INSERT INTO board_table (title, content, regDate, modDate, writer) values(?,?,?,?,?)";
		
			pstmt = conn.prepareStatement(insert_sql);
			//sql 쿼리
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, regDate);
			pstmt.setString(4, modDate);
			pstmt.setString(5, writer);
			pstmt.executeUpdate();   
		} catch (SQLException e1) {  
		} // 첨부 파일 제외 write 처리 기능 
		
		/* 파일 첨부 관련 처리 */
		// 아까 등록한 게시물의 index를 들고 와서 (auto increment id, content_id, file name)을 insert한다.		 
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