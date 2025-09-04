package package01;

import java.util.ArrayList;
import java.util.Scanner;

public class InformationManagementSystem {
	//创建集合储存账户
	private static ArrayList<Account> accounts=new ArrayList<>();
	
	//创建集合储存信息
	private static ArrayList<Person> persons=new ArrayList<Person>();
	
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
			return enterInfo();
		case 2:
			//查询信息
			return queryInfo();
		case 3:
			//删除信息
			return deleteInfo();
		case 4:
			//浏览信息
			return browseInfo();
		case 5:
			//退出系统
			return false;
		default:
			System.out.println("输入错误，请重新选择!");
			return true;
		}
	}
	
	//浏览信息
	public static boolean browseInfo() {
		if(persons.size()==0) {
			System.out.println("当前无信息可浏览！");
			return true;
		}
		for(int i=0;i<persons.size();i++) {
			Person person=persons.get(i);
			if(person instanceof Student) {
				person=(Student) person;
				System.out.println(person.toString());
			}
			else if(person instanceof Teacher){
				person=(Teacher) person;
				System.out.println(person.toString());
			}
		}
		return true;
	}
	
	//删除信息
	public static boolean deleteInfo() {
		if(persons.size()==0) {
			System.out.println("当前无信息可删除！");
			return true;
		}
		
		System.out.println("请输入您要删除的信息类型：\n1.学生\n2.教师");
		int choice=scanner.nextInt();
		scanner.nextLine();
		if(choice!=1&&choice!=2) {
			System.out.println("输入错误！");
			return true;
		}
		
		System.out.print("请输入您要删除的姓名：");
		String nameString=scanner.nextLine();
		
		for(int i=0;i<persons.size();i++) {
			Person person=persons.get(i);
			
			//查询到信息
			if(choice==1&&person instanceof Student&&person.getName().equalsIgnoreCase(nameString)) {
				persons.remove(i);
				System.out.println("学生 “"+nameString+"” 信息删除成功");
				return true;
			}
			if(choice==2&&person instanceof Teacher&&person.getName().equalsIgnoreCase(nameString)) {
				persons.remove(i);
				System.out.println("教师 “"+nameString+"” 信息删除成功");
				return true;
			}
		}
		//未查询到信息
		System.out.println("删除 “"+nameString+"” 信息失败");
		return true;
	}

	//查询信息
	public static boolean queryInfo() {
		if(persons.size()==0) {
			System.out.println("当前无信息可查询！");
			return true;
		}
		
		System.out.print("请输入您要访问的姓名：");
		String nameString=scanner.nextLine();
		
		boolean findStudent=false;
		boolean findTeacher=false;
		for(int i=0;i<persons.size();i++) {
			Person person=persons.get(i);
			
			//查询到信息
			if(person.getName().equalsIgnoreCase(nameString)) {
				if(!findStudent&&person instanceof Student) {
					findStudent=true;
					person=(Student) person;
					System.out.println(person.toString());
				}
				else if(!findTeacher&&person instanceof Teacher){
					findTeacher=true;
					person=(Teacher) person;
					System.out.println(person.toString());
				}
			}
			if(findStudent&&findTeacher) break;
		}
		//未查询到信息
		if(!findStudent&&!findTeacher) {
			System.out.println("查询 “"+nameString+"” 信息失败");
		}
		
		return true;
	}
	
	//录入信息
	public static boolean enterInfo() {
		System.out.println("请输入您要录入的信息类型：\n1.学生\n2.教师");
		int choice=scanner.nextInt();
		scanner.nextLine();
		
		String nameString,genderString,addressString,idString;
		if(choice==1) {
			System.out.print("请输入学生姓名：");
			nameString=scanner.nextLine();
			
			for(int i=0;i<persons.size();i++) {
				Person person=persons.get(i);
				
				//查询到信息
				if(person instanceof Student&&person.getName().equalsIgnoreCase(nameString)) {
					System.out.println("学生 “"+nameString+"” 已存在，录入信息失败!");
					return true;
				}
			}
			
			System.out.print("请输入学生性别：");
			genderString=scanner.nextLine();
			System.out.print("请输入学生籍贯：");
			addressString=scanner.nextLine();
			System.out.print("请输入学生学号：");
			idString=scanner.nextLine();
			
			Student student=new Student(nameString,genderString,addressString,idString);
			persons.add(student);
		}
		else if(choice==2) {
			System.out.print("请输入教师姓名：");
			nameString=scanner.nextLine();
			
			for(int i=0;i<persons.size();i++) {
				Person person=persons.get(i);
				
				//查询到信息
				if(person instanceof Teacher&&person.getName().equalsIgnoreCase(nameString)) {
					System.out.println("教师 “"+nameString+"” 已存在，录入信息失败!");
					return true;
				}
			}
			
			System.out.print("请输入教师性别：");
			genderString=scanner.nextLine();
			System.out.print("请输入教师籍贯：");
			addressString=scanner.nextLine();
			System.out.print("请输入教师工号：");
			idString=scanner.nextLine();
			Teacher teacher=new Teacher(nameString,genderString,addressString,idString);
			persons.add(teacher);
		}
		else {
			System.out.println("录入信息失败!");
		}
		return true;
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
		if(accounts.size()==0) return false;
		while(count--!=0) {
			System.out.print("请输入账号：");
			String usernameString=scanner.nextLine();
			System.out.print("请输入密码：");
			String passordString=scanner.nextLine();
			
			for(int i=0;i<accounts.size();i++) {
				if(usernameString.equals(accounts.get(i).getUsername())
				   &&passordString.equals(accounts.get(i).getPassword())) {
					return true;
				}
			}
			System.out.println("账户或者密码错误，请重试!");
			System.out.println("你还有"+count+"次重试机会!");
		}
		return false;
	}
    
    //注册
	public static boolean register() {
		System.out.print("请输入账号：");
		String usernameString=scanner.nextLine();
		for(int i=0;i<accounts.size();i++) {
			if(usernameString.equals(accounts.get(i).getUsername())) {
			     System.out.println("用户已存在，注册失败！");
			     return false;
			}
		}
		System.out.print("请输入密码：");
		String passwordString=scanner.nextLine();
		
		Account account=new Account(usernameString, passwordString);
		accounts.add(account);
		
		return true;
	}
}
