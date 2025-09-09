package package01;

public class Account {
	private String username;
    private String password;
    
    public Account() {
    	
    }

	public Account(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "用户名：" + username + "  密码：" + password;
	}
}    
	
