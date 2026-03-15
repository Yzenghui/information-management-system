package package01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDAOJdbc {
	//浏览学生信息
	public ArrayList<Student> listStudents() throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		//定义集合存储结果
		ArrayList<Student> students=new ArrayList<Student>();
		
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			pstmt=connection.prepareStatement("select * from students");
					
			//执行查询操作
            ResultSet resultSet=pstmt.executeQuery();
					
            //将结果集添加进集合
            while(resultSet.next()) {
            	//创建人员对象
            	Student student=new Student();
            		
            	//赋值
            	student.setId(resultSet.getString("stu_id"));//学号
            	student.setName(resultSet.getString("name"));//姓名
            	student.setGender(resultSet.getString("gender"));//性别
            	student.setMajor(resultSet.getString("major"));//专业
            	student.setProfession(resultSet.getString("profession"));//职业
            	student.setAddress(resultSet.getString("address"));//地址
            	
            	//加入集合
            	students.add(student);
            }
            return students;
		}
		finally {
			//关闭语句对象
			pstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
			
			//Java 语言规范禁止 finally “非正常结束”
			//故而不能在此return
		}	
	}

	//浏览教师信息
	public ArrayList<Teacher> listTeachers() throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		//定义集合存储结果
		ArrayList<Teacher> teachers=new ArrayList<Teacher>();
		
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			pstmt=connection.prepareStatement("select * from teachers");
					
			//执行查询操作
            ResultSet resultSet=pstmt.executeQuery();
					
            //将结果集添加进集合
            while(resultSet.next()) {
            	//创建人员对象
            	Teacher teacher=new Teacher();
            		
            	//赋值
            	teacher.setId(resultSet.getString("tea_id"));//学号
            	teacher.setName(resultSet.getString("name"));//姓名
            	teacher.setGender(resultSet.getString("gender"));//性别
            	teacher.setSubject(resultSet.getString("subject"));//所授学科
            	teacher.setProfession(resultSet.getString("profession"));//职业
            	teacher.setAddress(resultSet.getString("address"));//地址
            	
            	//加入集合
            	teachers.add(teacher);
            }
            return teachers;
		}
		finally {
			//关闭语句对象
			pstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}	
	}

	//浏览全部人员信息
	public ArrayList<Person> listAllPersons() throws SQLException {
		//定义集合存储
		ArrayList<Person> persons=new ArrayList<Person>();
		
		//合并学生信息和教师信息
		persons.addAll(listStudents());//添加学生信息
		persons.addAll(listTeachers());//添加教师信息
		
		//返回信息集合
		return persons;
	}

    //查询信息
	public ArrayList<Person> queryPersons(String name) throws SQLException{
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
				
		//定义集合存储结果
		ArrayList<Person> persons=new ArrayList<Person>();
		
		try {
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			pstmt=connection.prepareStatement("select stu_id as id, name, gender, major as course, profession, address from students where name=?"+" union all "+
					"select tea_id as id, name, gender, subject as course, profession, address from teachers where name=?");//联合查询语句
			
			//设置参数
			pstmt.setString(1, name);
			pstmt.setString(2, name);
			
			//获取结果集
			ResultSet resultSet=pstmt.executeQuery();
			
			//将查询结果添加进集合
			 while(resultSet.next()) {
            	//获取信息职业
				String profession=resultSet.getString("profession");
            		
            	if(profession.equals("学生")) {
            		//创建学生对象
                	Student student=new Student();
                		
                	//赋值
                	student.setId(resultSet.getString("id"));//学号
                	student.setName(resultSet.getString("name"));//姓名
                	student.setGender(resultSet.getString("gender"));//性别
                	student.setMajor(resultSet.getString("course"));//专业
                	student.setProfession(resultSet.getString("profession"));//职业
                	student.setAddress(resultSet.getString("address"));//地址
                	
                	//加入集合
                	persons.add(student);
            	}
            	else if(profession.equals("教师")){
            		//创建教师对象
                	Teacher teacher=new Teacher();
                		
                	//赋值
                	teacher.setId(resultSet.getString("id"));//学号
                	teacher.setName(resultSet.getString("name"));//姓名
                	teacher.setGender(resultSet.getString("gender"));//性别
                	teacher.setSubject(resultSet.getString("course"));//所授学科
                	teacher.setProfession(resultSet.getString("profession"));//职业
                	teacher.setAddress(resultSet.getString("address"));//地址
                	
                	//加入集合
                	persons.add(teacher);
            	}
	        }
			 
			//返回结果集合
			 return persons;
		}
		finally {
			//关闭语句对象
			pstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}
	}
	
	//根据学生学号来删除信息
	public boolean deleteByStuId(String id,ArrayList<Person> person) throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement selectPstmt=null;
		PreparedStatement deletePstmt=null;
	
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			selectPstmt=connection.prepareStatement("select * from students where stu_id=?");
			deletePstmt=connection.prepareStatement("delete from students where stu_id=?");

            //设置参数
			selectPstmt.setString(1, id);
			deletePstmt.setString(1, id);
			
			//记录要删除的信息
			ResultSet resultSet=selectPstmt.executeQuery();
			if(resultSet.next()) {
            	//创建人员对象
            	Student student=new Student();
            		
            	//赋值
            	student.setId(resultSet.getString("stu_id"));//学号
            	student.setName(resultSet.getString("name"));//姓名
            	student.setGender(resultSet.getString("gender"));//性别
            	student.setMajor(resultSet.getString("major"));//专业
            	student.setProfession(resultSet.getString("profession"));//职业
            	student.setAddress(resultSet.getString("address"));//地址
            	
            	person.add(student);
	        }
			
			//执行删除操作
			int deleteResult=deletePstmt.executeUpdate();
			
			return deleteResult>0;
		}
		finally {
			//关闭语句对象
			deletePstmt.close();
			selectPstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}	
	}

	//根据教师工号来删除信息
	public boolean deleteByTeaId(String id,ArrayList<Person> person) throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement selectPstmt=null;
		PreparedStatement deletePstmt=null;
	
		//开始数据库操作
		try{
			//获取连接对象与语句对象
			connection=DatabaseUtil.getConnection();
			selectPstmt=connection.prepareStatement("select * from teachers where tea_id=?");
			deletePstmt=connection.prepareStatement("delete from teachers where tea_id=?");

            //设置参数
			selectPstmt.setString(1, id);
			deletePstmt.setString(1, id);
			
			//记录要删除的信息
			ResultSet resultSet=selectPstmt.executeQuery();
			if(resultSet.next()) {
            	//创建人员对象
            	Teacher teacher=new Teacher();
            		
            	//赋值
            	teacher.setId(resultSet.getString("tea_id"));//学号
            	teacher.setName(resultSet.getString("name"));//姓名
            	teacher.setGender(resultSet.getString("gender"));//性别
            	teacher.setSubject(resultSet.getString("subject"));//所授学科
            	teacher.setProfession(resultSet.getString("profession"));//职业
            	teacher.setAddress(resultSet.getString("address"));//地址
            	
            	person.add(teacher);
	        }
			
			//执行删除操作
			int deleteResult=deletePstmt.executeUpdate();
			
			return deleteResult>0;
		}
		finally {
			//关闭语句对象
			deletePstmt.close();
			selectPstmt.close();
			
			//关闭连接
			DatabaseUtil.closeConnection(connection);
		}	
	}

    //添加人员信息
	public boolean addPerson(Person person) throws SQLException {
		//定义连接对象与语句对象
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		//开始数据库操作
		try{
			//获取连接对象
			connection=DatabaseUtil.getConnection();

			//若是插入学生信息
			if(person instanceof Student) {
				//获取语句对象
				pstmt=connection.prepareStatement("insert into students (stu_id, name, gender, major, profession, address)values (?,?,?,?,?,?)");
			    
				//强转
				Student student=(Student) person;
				
				//设置参数
				pstmt.setString(1,student.getId());
				pstmt.setString(2,student.getName());
				pstmt.setString(3,student.getGender());
				pstmt.setString(4,student.getMajor());
				pstmt.setString(5,student.getProfession());
				pstmt.setString(6,student.getAddress());
			}
			//若是插入学生信息
			else if(person instanceof Teacher) {
				//获取语句对象
				pstmt=connection.prepareStatement("insert into teachers (tea_id, name, gender, subject, profession, address)values (?,?,?,?,?,?)");
			    
				//强转
				Teacher teacher=(Teacher) person;
				
				//设置参数
				pstmt.setString(1,teacher.getId());
				pstmt.setString(2,teacher.getName());
				pstmt.setString(3,teacher.getGender());
				pstmt.setString(4,teacher.getSubject());
				pstmt.setString(5,teacher.getProfession());
				pstmt.setString(6,teacher.getAddress());
				
			}
			
		    //执行sql语句
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
