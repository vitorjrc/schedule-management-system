package schedulemanager.model;

/*
 * Class that represents a teacher who coordinates a Course
 */
public class Teacher extends User {
	
	private String courseManagedID; // ID of Course this teacher manages
	
	private Teacher(String id, String name, String password, String courseManagedID) {
		
		super(id, name, password);
		
		this.courseManagedID = courseManagedID;
	}
	
	private Teacher(Teacher t) {
		
		super(t);
		
		this.courseManagedID = t.getCourseManagedID();
	}
	
	public String getCourseManagedID() { return this.courseManagedID; }
}
