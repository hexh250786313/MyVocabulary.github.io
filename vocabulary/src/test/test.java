package test;

import java.sql.Connection;  
import java.sql.DriverManager;  
  
public class test {  
      public static void main(String args[]){
	     String driverName = "com.mysql.jdbc.Driver";  
	     String dbURL = "jdbc:mysql://localhost:3306/MyVocabulary?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	     String userName = "root";  
	     String userPwd = "250786";
	      
	     try{
	    	 Class.forName(driverName);
	    	 Connection dbConn = DriverManager.getConnection(dbURL,userName,userPwd);
	    	 System.out.println("success!");
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
      }
}
