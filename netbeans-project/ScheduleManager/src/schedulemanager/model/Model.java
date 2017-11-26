package schedulemanager.model;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */

import java.util.*;

public class Model {
    
    private LinkedHashMap<String, Course> coursesList = new LinkedHashMap<String, Course>(); // Course ID -> Course
    private HashMap<String, Student> studentsList = new HashMap<String, Student>(); // Student ID -> Student
	
    private AuthManager authManager;
    private SwapManager swapManager;
	
	public Model() {
		this.authManager = new AuthManager();
		this.swapManager = new SwapManager(authManager);
	}
    
    public HashMap<String, Student> getStudents() {
        return this.studentsList;
    }
    
    public LinkedHashMap<String, Course> getCourses() {
        
        return new LinkedHashMap<String, Course>(this.coursesList);
    }
    
    public boolean directSwap(String studentID, String courseID, String fromShiftID, String toShiftID) {
    	
    	return this.swapManager.directSwap(studentID, courseID, fromShiftID, toShiftID, (HashMap<String, Course>) this.coursesList);
    }
    
    public Student registerStudent(String name, String id, String pass, String stat){
        Student newStudent = new Student(name, id, pass, stat);
        studentsList.put(id, newStudent);
        
        return newStudent;
    }
    
    public Course createCourse(String courseID, String courseName) {
    	
    	Course newCourse = new Course(courseID, courseName);
    	
    	this.coursesList.put(courseID, newCourse);
    	
    	return newCourse;
    } 
}
