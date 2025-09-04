package package01;

public class Student extends Person {
    private String id;//学号
	
	public Student() {
    	
    }

	public Student(String name,String gender,String address,String id) {
		super(name,gender,address);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "学生姓名" + getName() + "  性别" + getGender() + "  籍贯" + getAddress()+"  学号："+getId();
	}
	
	
}
