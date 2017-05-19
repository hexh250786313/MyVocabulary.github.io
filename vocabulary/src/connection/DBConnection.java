package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
  //指定驱动程序
	private static String driver= "com.mysql.jdbc.Driver"; 
  //创建指定数据库的URL,连接服务器和指定数据库bookmanage
	private static String url= "jdbc:mysql://localhost:3306/MyVocabulary?useUnicode=true&characterEncoding=utf-8&useSSL=false";
  //提供用户名和密码
	private static String user= "root";         
	private static String password= "250786";
	public static Connection getConnection(){
		Connection con;
		try{
			//加载驱动程序
			Class.forName(driver);
			//创建连接
			con=DriverManager.getConnection(url,user,password);
			return con;
		}catch(ClassNotFoundException e){
			System.out.println(e.getMessage());
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return null;
	}
}