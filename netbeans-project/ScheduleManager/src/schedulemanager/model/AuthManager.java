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
	
	// Admin login credentials. Not safe in current form.
	private static final String adminID = "admin";
	private static final String adminPassword = "SECURITYHOLE";
	
	public AuthManager() {
		// Nothing to do here
	}
	
	public void registerStudent(String id, String name, String password, String regimen, ArrayList<String> courseIDs) {
		
		Student newStudent = new Student(id, name, password, regimen, courseIDs);
		
		registeredStudents.add(newStudent);
	}
	
	// Returns true if login was successful, false otherwise
	public boolean login(String id, String password) {
		
		// Check if this is an admin login attempt
		if (id == this.adminID) {
			if (password == this.adminPassword) {
				
				this.isAdminLoggedIn = true;
				this.isStudentLoggedIn = false;
				this.loggedInStudent = null;
				
				return true;
			
			} else {
				
				return false; // Tried to login as admin but password is wrong
			}
		}
		
		// Check if a student exists with the given ID
		for (Student student: this.registeredStudents) {

			if (student.getID() == id) {
				
				// Check password
				if (student.getPassword() == password) {
					
					this.isStudentLoggedIn = true;
					this.isAdminLoggedIn = false; // Just to make sure
					
					this.loggedInStudent = student;
					
					return true;
					
				} else {
					
					// Here we can add extra wrong password logic,
					// such as counting failed attempts
					return false;
				}
			}
		}
		
		return false; // Didn't find student with received ID
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
