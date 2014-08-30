import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


public class Main {
	public static final String DRIVER="oracle.jdbc.driver.OracleDriver";  
    //数据库连接地址(数据库名)  
    public static final String URL="jdbc:oracle:thin:@127.0.0.1:1521:orcl";  
    //登陆名  
    public static final String USER="SCOTT";  
    //登陆密码  
    public static final String PWD="tiger";  
    //创建数据库连接对象  
    private Connection con=null;  
    //创建数据库预编译对象  
    private PreparedStatement ps=null;  
    //创建结果集  
    private ResultSet rs=null;  
    //创建数据源对象  
    public static DataSource source=null;
	public Main(){
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection(URL,USER,PWD);
			ps=con.prepareStatement("select * from stu");  
            rs=ps.executeQuery();
            while(rs.next()) {
            	  int num =rs.getInt("STUID");
            	  System.out.println(num);
            	} 
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
