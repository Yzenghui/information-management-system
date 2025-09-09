package package01;

public class Teacher extends Person{
    private String id;//工号
    private String subject;//所授学科
    
    public Teacher() {
    	
    }

	public Teacher(String name,String gender,String profession,String address,String id,String subject) {
		super(name,gender,profession,address);
		this.id = id;
		this.subject=subject;
	}
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "工号：" + getId() + "  教师姓名：" + getName() + "  性别：" + getGender() + "  所授学科：" + getSubject()+ "  职业：" + getProfession() + "  籍贯：" + getAddress();
	}
}
