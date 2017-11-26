package schedulemanager.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages authentication stuff
 */
public class AuthManager {
	
	private boolean isStudentLoggedIn = false;
	private boolean isAdminLoggedIn = false;
	private Student loggedInStudent = null;
	private HashMap<String, Student> registeredStudents; // StudentID -> Student
	
	// Admin login credentials. Not safe in current form.
	private static final String adminID = "admin";
	private static final String adminPassword = "SECURITYHOLE";
	
	public AuthManager() {
		
		this.registeredStudents = new HashMap<String, Student>();
	}
	
	public void registerStudent(String id, String name, String password, String regimen, ArrayList<String> courseIDs) {
		
		Student newStudent = new Student(id, name, password, regimen, courseIDs);
		
		registeredStudents.put(id, newStudent);
	}
	
	public Student getStudentByID(String id) {
		
		return this.registeredStudents.get(id);
	}
	
	// Returns true if login was successful, false otherwise
	public boolean login(String id, String password) {
		
		// Check if this is an admin login attempt
		if (id.equals(AuthManager.adminID)) {
			if (password.equals(AuthManager.adminPassword)) {
				
				this.isAdminLoggedIn = true;
				this.isStudentLoggedIn = false;
				this.loggedInStudent = null;
				
				return true;
			
			} else {
				
				return false; // Tried to login as admin but password is wrong
			}
		}
		
		// Check if a student exists with the given ID
		if (this.registeredStudents.containsKey(id)) {
			
			Student student = this.registeredStudents.get(id);
				
			// Check password
			if (student.getPassword().equals(password)) {
				
				this.isStudentLoggedIn = true;
				this.isAdminLoggedIn = false; // Just to make sure
				this.loggedInStudent = student;
				
				return true;
				
			} else {
				
				// Here we can add extra wrong password logic,
				// such as counting failed attempts
				return false;
			}
			
		} else {
		
			return false; // Didn't find student with received ID
		}
	}
	
	public void logout() {
		
		this.isAdminLoggedIn = false;
		this.isStudentLoggedIn = false;
		this.loggedInStudent = null;
	}
	
	public boolean isStudentLoggedIn() {
		
		return this.isStudentLoggedIn;
	}
	
	public boolean isAdminLoggedIn() {
		
		return this.isAdminLoggedIn;
	}
	
	public Student getLoggedInStudent() {
		
		return this.loggedInStudent.clone();
	}

}
