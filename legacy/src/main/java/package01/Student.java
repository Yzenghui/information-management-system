package package01;

public class Student extends Person {
    private String id;//学号
    private String major;//学科
	
	public Student() {
    	
    }

	public Student(String name,String gender,String profession,String address,String id,String major) {
		super(name,gender,profession,address);
		this.id = id;
		this.major=major;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public String toString() {
		return "学号：" + getId() + "  学生姓名：" + getName() + "  性别：" + getGender() + "  专业：" + getMajor() + "  职业：" + getProfession() + "  籍贯：" + getAddress();
	}
}
