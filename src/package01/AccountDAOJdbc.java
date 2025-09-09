package package01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//DAO类：只包含纯数据库操作
//主类：处理用户交互、业务逻辑、界面显示

public class AccountDAOJdbc {
	//验证是否存在某用户
	public boolean checkUserExists(String username,String password) throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			pstmt=connection.prepareStatement("select * from Accounts where username=?&&password=?");
			
			//设置参数
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			//获取结果集
			ResultSet resultSet=pstmt.executeQuery();
			
			return resultSet.next();
		}
		finally {
			//关闭语句对象
			pstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}
	}

    //验证用户名是否存在
	public boolean checkUsernameExists(String username) throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
				
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			pstmt=connection.prepareStatement("select * from Accounts where username=?");
					
			//设置参数
			pstmt.setString(1, username);
					
			//获取结果集
			ResultSet resultSet=pstmt.executeQuery();
					
			return resultSet.next();
		}
		finally {
			//关闭语句对象
			pstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}	
	}

	//注册用户
	public boolean addAccount(String username,String password) throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
				
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			pstmt=connection.prepareStatement("insert into Accounts values(?,?)");
					
			//设置参数
			pstmt.setString(1, username);
			pstmt.setString(2, password);
					
			//执行更新操作
            int addResult=pstmt.executeUpdate();
					
			return addResult>0;
		}
		finally {
			//关闭语句对象
			pstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}	
	}
}
