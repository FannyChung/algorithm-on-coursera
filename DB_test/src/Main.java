import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;


public class Main {
	public static final String DRIVER="oracle.jdbc.driver.OracleDriver";  
    //���ݿ����ӵ�ַ(���ݿ���)  
    public static final String URL="jdbc:oracle:thin:@127.0.0.1:1521:orcl";  
    //��½��  
    public static final String USER="SCOTT";  
    //��½����  
    public static final String PWD="tiger";  
    //�������ݿ����Ӷ���  
    private Connection con=null;  
    //�������ݿ�Ԥ�������  
    private PreparedStatement ps=null;  
    //���������  
    private ResultSet rs=null;  
    //��������Դ����  
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
