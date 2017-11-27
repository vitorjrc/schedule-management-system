package schedulemanager.model;
import java.io.Serializable;

public class User implements Serializable {
	
        private static final long serialVersionUID = 7526472295622776147L;
	protected String id;
	protected String name;
	protected String password;
	
	public User(String id, String name, String password) {
		this.name = name;
        this.id = id;
        this.password = password;
	}
	
	public User(User u) {
		this.name = u.getName();
		this.id = u.getID();
		this.password = u.getPassword();
	}
	
	public String getName() { return this.name; }
	    
    public String getID() { return this.id; }
	    
	public String getPassword() { return this.password; }
}
