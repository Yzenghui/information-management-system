package package01;

public class Teacher extends Person{
    private String id;//工号
    
    public Teacher() {
    	
    }

	public Teacher(String name,String gender,String address,String id) {
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
		return "教师姓名" + getName() + "  性别" + getGender() + "  籍贯" + getAddress()+"  工号："+getId();
	}
}
