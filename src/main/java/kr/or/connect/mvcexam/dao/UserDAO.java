package kr.or.connect.mvcexam.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

	Connection conn = null; 
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	String driverName = "com.mysql.jdbc.Driver";
 	String dbUrl = "jdbc:mysql://localhost:3306/boarddb2?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
	String dbUser = "root";
	String dbPassword = "root";
	String login_result="";
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
	
	public String login(String userId, String passWd) throws SQLException {
		
		ResultSet result = null;
		PreparedStatement pstmt;
		
		String login_sql = "SELECT * FROM UserTable where UserID=?";
		String db_passwd=""; //DB에서 가져 온 Passwd
		System.out.println("login!!");
		try {
			conn = this.getConnection();
			
			pstmt = conn.prepareStatement(login_sql);
			pstmt.setString(1, userId);
			
			result = pstmt.executeQuery();
			
			while(result.next()) {
				db_passwd = result.getString("UserPasswd");
			}
			
			System.out.println("DB passwd: "+db_passwd);
			System.out.println("passwd: "+passWd);
			
			if(passWd.equals(db_passwd)) {
				login_result = "success";
			} 
			else if(db_passwd.length()==0){  //id가 없는 경우
				login_result = "noData";
			}
			else { //id 자체가 없는 경우
				login_result = "fail";
			}

		} catch(Exception e) {}
		finally {
			if(conn!=null) conn.close();
		}
		return login_result;
	}
	public void logout() { 
		
	}
}
