package vocabulary;

import java.sql.*;
import connection.DBConnection;

public class MaxNumber{
	public int getMaxNumber(){
		int maxnumber = 0;
		String sql="select max(number) from vocabulary";
		Statement stmt;
		Connection conn;
		try{
			conn=DBConnection.getConnection();
			stmt=conn.createStatement();
			//执行SQL语句并将查询结果放入结果集中
			ResultSet rs=stmt.executeQuery(sql);
			//将结果集中的数据取出放入User类的对象中
			if(rs.next()) maxnumber = rs.getInt("max(number)");		
			stmt.close();
		    conn.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return maxnumber;
	}
}
