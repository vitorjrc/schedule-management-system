package schedulemanager.model;

import java.util.ArrayList;

/**
 * Manages authentication stuff
 */
public class AuthManager {
	
	private boolean isStudentLoggedIn = false;
	private boolean isAdminLoggedIn = false;
	private Student loggedInStudent = null;
	private ArrayList<Student> registeredStudents;
	
	private static final String adminPassword = "SECURITYHOLE";
	
	public AuthManager() {
		// Nothing to do here
	}
	
	public void registerStudent(String id, String name, String password, String regimen, ArrayList<String> courseIDs) {
		
		Student newStudent = new Student(id, name, password, regimen, courseIDs);
		
		registeredStudents.add(newStudent);
	}
	
	public void login() {
		// todo
	}
	
	public boolean isStudentLoggedIn() {
		// todo
	}
	
	public boolean isAdminLoggedIn() {
		// todo
	}
	
	public Student getLoggedInStudent() {
		// todo
	}

}
