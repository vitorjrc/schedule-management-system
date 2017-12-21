package schedulemanager.model;

import schedulemanager.db.CourseDAO;

/*
 * Class that represents a teacher who coordinates a Course
 */
public class Teacher extends User {
	
	private static final long serialVersionUID = 7526472295622776147L;
	
	private CourseDAO courseDAO;
	
	public Teacher(String id, String name, String password) {
		
		super(id, name, password);
		
		this.courseDAO = new CourseDAO();
	}
	
	public Teacher(Teacher t) {
		
		super(t);
		
		this.courseDAO = new CourseDAO();
	}
	
	public String getCourseManagedID() {
		return this.courseDAO.getCourseManaged(this.id);
	}
        
    public void setManagedCourseID (String courseID) {
    	this.courseDAO.setCourseManager(this.id, courseID);
    }

    public Teacher clone() {
    	return new Teacher(this);
    }
}
