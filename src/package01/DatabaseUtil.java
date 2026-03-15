package package01;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DatabaseUtil {
    private static DataSource dataSource;//连接池
    
    //私有化构造方法，方式实例化
    private DatabaseUtil() {};
    
    //静态代码块
    static {
    	//加载配置文件
    	Properties prop=new Properties();
    	InputStream inputStream=DatabaseUtil.class.getClassLoader().getResourceAsStream("druid.properties");
    	try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	//获取连接池对象
    	try {
			dataSource=DruidDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //获取连接
    public static Connection getConnection() throws SQLException {
    	return dataSource.getConnection();
    }
    
    //关闭连接
    public static void closeConnection(Connection connection) throws SQLException {
    	if(connection!=null) {
    		connection.close();
    	}
    }
}
