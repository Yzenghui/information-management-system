package package01;

public class Person {
    private String name;   //姓名
    private String gender; //性别
    private String address;//地址
    
    public Person() {
    	
    }
    
	public Person(String name, String gender, String address) {
		super();//默认先访问父类的无参构造
		this.name = name;
		this.gender = gender;
		this.address = address;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "姓名" + getName() + "  性别" + getGender() + "  籍贯" + getAddress();
	}
    
    
}
