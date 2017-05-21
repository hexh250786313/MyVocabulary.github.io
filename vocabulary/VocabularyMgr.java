package vocabulary;

import java.sql.*;
import connection.DBConnection;

public class VocabularyMgr{
    //向数据库中添加单词信息
	public void addVocabulary(vocabularymessage vocabularymessage){
		//构造SQL语句
		String sql="insert into vocabulary(number,vocabulary)" +
				"values('"+vocabularymessage.getNumber()+"','"+vocabularymessage.getVocabulary()+"')";
		Statement stmt;
		Connection conn;
		try{
			//获取数据库连接
			conn=DBConnection.getConnection();
			//创建Statement对象
			stmt=conn.createStatement();
			//执行SQL语句
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
//单词信息查询
	public vocabularymessage[] getVocabulary(){
		vocabularymessage vocabularymessage[];
		int i=-1;
		String sql="select * from vocabulary order by number asc";
		Statement stmt;
		Connection conn;
		vocabularymessage=new vocabularymessage[50000];
		try{
			conn=DBConnection.getConnection();
			stmt=conn.createStatement();
			
			//执行SQL语句并将查询结果放入结果集中
			ResultSet rs=stmt.executeQuery(sql);
			//将结果集中的数据取出放入User类的对象中
			while(rs.next()){
				i++;
				vocabularymessage[i]=new vocabularymessage();
				vocabularymessage[i].setNumber(rs.getInt("number"));
				vocabularymessage[i].setVocabulary(rs.getString("vocabulary"));
			}
			stmt.close();
		    conn.close();
		 return vocabularymessage;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
	public void updateVocabulary(String vocabulary,int number){
		//构造SQL语句
		String sql="update vocabulary" +
				" set vocabulary = '"+vocabulary+"' where number = '"+number+"'";
		Statement stmt;
		Connection conn;
		try{
			//获取数据库连接
			conn=DBConnection.getConnection();
			//创建Statement对象
			stmt=conn.createStatement();
			//执行SQL语句
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public vocabularymessage[] randVocabulary(){
		vocabularymessage vocabularymessage[];
		int i=-1;
		String sql="select * from vocabulary order by rand(number)";
		Statement stmt;
		Connection conn;
		vocabularymessage=new vocabularymessage[50000];
		try{
			conn=DBConnection.getConnection();
			stmt=conn.createStatement();
			
			//执行SQL语句并将查询结果放入结果集中
			ResultSet rs=stmt.executeQuery(sql);
			//将结果集中的数据取出放入User类的对象中
			while(rs.next()){
				i++;
				vocabularymessage[i]=new vocabularymessage();
				vocabularymessage[i].setNumber(rs.getInt("number"));
				vocabularymessage[i].setVocabulary(rs.getString("vocabulary"));
			}
			stmt.close();
		    conn.close();
		 return vocabularymessage;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
}