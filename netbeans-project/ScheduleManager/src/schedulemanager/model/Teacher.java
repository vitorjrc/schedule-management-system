package schedulemanager.model;

/*
 * Class that represents a teacher who coordinates a Course
 */
public class Teacher extends User {
	
	private static final long serialVersionUID = 7526472295622776147L;
	
	private String courseManagedID; // ID of Course this teacher manages
	
	public Teacher(String id, String name, String password) {
		
		super(id, name, password);
		
	}
	
	public Teacher(Teacher t) {
		
		super(t);
		
		this.courseManagedID = t.getCourseManagedID();
	}
	
	public String getCourseManagedID() { return this.courseManagedID; }
        
        public void setManagedCourseID (String course) {
            this.courseManagedID = course;
        }
	

        public Teacher clone() {
            return new Teacher(this);
    }
}
