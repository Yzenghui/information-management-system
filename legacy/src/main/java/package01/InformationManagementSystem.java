package package01;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class InformationManagementSystem {
	//获取DAO类对象
	static AccountDAOJdbc accountDAOJdbc=new AccountDAOJdbc();
	static PersonDAOJdbc personDAOJdbc=new PersonDAOJdbc();
	
	static Scanner scanner=new Scanner(System.in);

	public static void main(String[] args) {
		startSystem();
		while(true) {
			menuPrint();
			if(!taskChoose()) break;
		}
    }
	
	//业务选择
	public static boolean taskChoose() {
		int choice=scanner.nextInt();
		scanner.nextLine();
		switch(choice) {
		case 1:
		    //录入信息
			enterInfo();
			return true;
		case 2:
			//查询信息
			queryInfo();
			return true;
		case 3:
			//删除信息
			deleteInfo();
			return true;
		case 4:
			//浏览信息
			browseInfo();
			return true;
		case 5:
			//退出系统
			return false;
		default:
			System.out.println("输入错误，请重新选择!");
			return true;
		}
	}
	
	//打印菜单
 	public static void menuPrint() {
		System.out.println("---------欢迎使用信息管理系统---------");
		System.out.println("1.录入信息\n"
				         + "2.查询信息\n"
				         + "3.删除信息\n"
				         + "4.浏览信息\n"
				         + "5.退出系统");
	}

    //浏览信息
  	public static boolean browseInfo() {
  		//选择浏览信息类型
  		System.out.println("请选择浏览类型：\n1.学生\n2.教师\n3.全部");
  	    int choice = scanner.nextInt();
  	    scanner.nextLine();
  		
  	    //创建集合储存信息
  		ArrayList<? extends Person> persons=new ArrayList<Person>();
  	    
  	    try {
  	        //根据选择浏览相应信息
  	  	    switch(choice) {
  	  	    case 1:
  	  	    	//浏览学生信息
  	  	    	persons=personDAOJdbc.listStudents();
  	  	    	break;
  	  	    case 2:
  	  	    	//浏览教师信息
  	  	    	persons=personDAOJdbc.listTeachers();
  	  	        break;
  	  	    case 3:
  	  	    	//浏览全部人员信息
  	  	    	persons=personDAOJdbc.listAllPersons();
  	  	    	break;
  	  	    default:
  	  	    	//输入错误
  	  	    	System.out.println("输入错误，请重新选择!");
  	  	    	return false;
  	  	    }	
  	    }
  	    catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统出现异常，操作失败！");
			return false;
		}
  	    	
  	    //信息为空
  		if(persons.size()==0) {
  			System.out.println("当前无信息可浏览！");
  			return false;
  		}
  		
  		//信息不为空，打印集合内容
  		for(int i=0;i<persons.size();i++) {
  			Person person=persons.get(i);
  			System.out.println(person);
  		}
  		return true;
  	}
  	
    //查询信息
  	public static boolean queryInfo() {
  		//创建集合储存信息
  		ArrayList<Person> persons=new ArrayList<Person>();
  		
  		//指定作为查询条件的姓名
  		System.out.print("请输入您要访问的姓名：");
  		String nameString=scanner.nextLine();
  		
  		//开始查询
  		try {
			//获取查询结果集合
  			persons=personDAOJdbc.queryPersons(nameString);
  			
  			//若查询结果为空
  			if(persons.size()==0) {
  	  			System.out.println("未查询到相关信息！");
  	  			return false;
  	  		}
  		    //查询到信息,打印集合
  			else {
  				for(int i=0;i<persons.size();i++) {
  		  			Person person=persons.get(i);
  		  			System.out.println(person);
  		  		}
  			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统出现异常，操作失败！");
			return false;
		}
  		return true;
  	}
  	
  	//删除信息
  	public static boolean deleteInfo() {
  	    //选择删除信息类型
  		System.out.println("请选择删除类型：\n1.学生\n2.教师");
  	    int choice = scanner.nextInt();
  	    scanner.nextLine();
  		
  		//执行数据库操作
  		try {
  			//定义字符串来记录学生学号或教师工号
  			String idString=new String();
  			
  			//定义集合来记录删除的单个人员信息
  			ArrayList<Person> person=new ArrayList<Person>();
  			
  			//根据选择删除相应信息
  	  	    switch(choice) {
  	  	    case 1://删除学生
  	  	        //输入要删除的学生学号
  	  		    System.out.print("请输入您要删除的学生学号：");
  	    		idString=scanner.nextLine();
  	    	
  	    		//删除成功
  	    		if(personDAOJdbc.deleteByStuId(idString, person)) {
  	    			//打印删除结果
  	    			System.out.println("信息 "+person.get(0)+" 删除成功！");
  	    			
  	    			return true;
  	    		}
  	    	    //未查询到信息,删除失败
  	    		else {
  	    			System.out.println("未查询到信息,删除失败！");
  	    			
  	    			return false;
  	    		}
  	  	    case 2:
  	  	        //输入要删除的教师工号
  	  		    System.out.print("请输入您要删除的教师工号：");
  	    		idString=scanner.nextLine();
                
  	    	    //删除成功
  	    		if(personDAOJdbc.deleteByTeaId(idString, person)) {
  	    			//打印删除结果
  	    			System.out.println("信息 "+person.get(0)+" 删除成功！");
  	    			
  	    			return true;
  	    		}
  	    	    //未查询到信息,删除失败
  	    		else {
  	    			System.out.println("未查询到信息,删除失败！");
  	    			
  	    			return false;
  	    		}
  	  	    default:
  	  	    	//输入错误
  	  	    	System.out.println("输入错误，请重新选择!");
  	  	    	return false;
  	  	    }	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统出现异常，操作失败！");
			return false;
		}
  	}

  	//查询信息
  	
  	//录入信息
  	public static boolean enterInfo() {
  	    //选择浏览信息类型
  		System.out.println("请输入您要录入的信息类型：\n1.学生\n2.教师");
  		int choice=scanner.nextInt();
  		scanner.nextLine();
  		
  		//创建Person对象来记录信息
  		Person person=new Person();
  		
  		//根据选择录入相应信息
  	    switch(choice) {
  	    case 1:{
  	        //录入学生信息
  	    	System.out.print("请输入学生学号：");
  			String idString=scanner.nextLine();
  			System.out.print("请输入学生姓名：");
  			String nameString=scanner.nextLine();
  			System.out.print("请输入学生性别：");
  			String genderString=scanner.nextLine();
  			System.out.print("请输入学生专业：");
  			String majorString=scanner.nextLine();
  			System.out.print("请输入学生地址：");
  			String addressString=scanner.nextLine();
  	    	
  			person=new Student(nameString, genderString,"学生", addressString, idString, majorString);
  			
  	    	break;
  	    }
  	    case 2:
  	    	//录入教师信息
  	    	System.out.print("请输入教师工号：");
  			String idString=scanner.nextLine();
  			System.out.print("请输入教师姓名：");
  			String nameString=scanner.nextLine();
  			System.out.print("请输入教师性别：");
  			String genderString=scanner.nextLine();
  			System.out.print("请输入教师所授学科：");
  			String subjectString=scanner.nextLine();
  			System.out.print("请输入教师地址：");
  			String addressString=scanner.nextLine();
  	    	
  			person=new Teacher(nameString, genderString,"教师", addressString, idString, subjectString);
  	        break;
  	    default:
  	    	//输入错误
  	    	System.out.println("输入错误，请重新选择!");
  	    	return false;
  	    }	
  		
  		//执行数据库操作
  	    try {
			//录入信息
  	    	boolean addResult=personDAOJdbc.addPerson(person);
  	    	
  	    	//录入成功
  	    	if(addResult) {
  	    		System.out.println("录入成功！");
  	    		return true;
  	    	}
  	    	//录入失败
  	    	else {
  	    		System.out.println("录入失败！");
  	    		return false;
  	    	}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统出现异常，操作失败！");
			return false;
		}
  	}
    
    //登录
    
    //开始系统
    public static boolean startSystem() {
    	while(true) {
    		System.out.println("请输入您的选择：\n" + "1.登录\n"+ "2.注册");
        	int choice=scanner.nextInt();
        	scanner.nextLine();
        	switch(choice) {
        	case 1:
        		//登录
        		if(login()) {
        			System.out.println("登录成功！");
        			return true;
        		}
        		else {
        			System.out.println("登录失败！");
        		}
        		break;
        	case 2:
        		//注册
        		if(register()) {
        			System.out.println("注册成功！");
        		}
        		else {
        			System.out.println("注册失败！");
        		}
        		break;
        	default:
        		System.out.println("输入错误，请重新选择!");
        		break; 
        	}
    	}
    }
  	
  	//登录
  	public static boolean login() {
    	int count=3;
		while(count--!=0) {
			System.out.print("请输入账号：");
			String usernameString=scanner.nextLine();
			System.out.print("请输入密码：");
			String passordString=scanner.nextLine();
			
			try {
				//使用DAO方法判断登录结果
				boolean checkResult=accountDAOJdbc.checkUserExists(usernameString, passordString);
				
				if(checkResult) return true;//登录成功
				else {//登录失败
					System.out.println("账户或者密码错误，请重试!");
					System.out.println("你还有"+count+"次重试机会!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("系统出现异常，操作失败！");
				return false;
			}
		}
		return false;
	}
	
    //注册
    public static boolean register() {
		System.out.print("请输入账号：");
		String usernameString=scanner.nextLine();
		
		try {
			//验证用户名是否存在
			boolean checkResult=accountDAOJdbc.checkUsernameExists(usernameString);
			if(checkResult) {
				System.out.println("用户" + usernameString + "已存在！");
			    return false;//用户名已存在，注册失败
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.print("请输入密码：");
		String passwordString=scanner.nextLine();
		
		try {
			//注册用户
			boolean addResult=accountDAOJdbc.addAccount(usernameString, passwordString);
			return addResult;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统出现异常，操作失败！");
			return false;
		}
	}

}
