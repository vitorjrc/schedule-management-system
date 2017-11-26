package schedulemanager.model;

import java.util.HashMap;

/**
 * Manages authentication stuff
 */
public class AuthManager {
	
	private boolean isStudentLoggedIn = false;
	private boolean isAdminLoggedIn = false;
	private boolean isTeacherLoggedIn = false;
	private User loggedInUser = null;
	
	private HashMap<String, Student> registeredStudents; // StudentID -> Student
	private HashMap<String, Teacher> registeredTeachers; // TeacherID -> Teacher
	
	// Admin login credentials. Not safe in current form.
	private static final String adminID = "admin";
	private static final String adminPassword = "SECURITYHOLE";
	
	public AuthManager() {
		
		this.registeredStudents = new HashMap<String, Student>();
		this.registeredTeachers = new HashMap<String, Teacher>();
	}
	
	public void registerStudent(String id, String name, String password, String regimen) {
		
		Student newStudent = new Student(id, name, password, regimen);
		
		registeredStudents.put(id, newStudent);
	}
	
	// Register a Teacher.
	// courseManagedID is the ID of the course the teacher manages.
	public void registerTeacher(String id, String name, String password, String courseManagedID) {
		
		Teacher newTeacher = new Teacher(id, name, password, courseManagedID);
		
		registeredTeachers.put(id, newTeacher);
	}
	
	public Student getStudentByID(String id) {
		
		return this.registeredStudents.get(id);
	}
	
	public Teacher getTeacherByID(String id) {
		
		return this.registeredTeachers.get(id);
	}
	
	// Returns true if login was successful, false otherwise
	public boolean login(String id, String password) {
		
		// Check if this is an admin login attempt
		if (id.equals(AuthManager.adminID)) {
			if (password.equals(AuthManager.adminPassword)) {
				
				this.isAdminLoggedIn = true;
				this.isStudentLoggedIn = false;
				this.loggedInUser = null;
				
				return true;
			
			} else {
				
				return false; // Tried to login as admin but password is wrong
			}
		}
		
		User loginUser;
		
		// Check if a student exists with the given ID
		if (this.registeredStudents.containsKey(id)) {
			
			loginUser = this.registeredStudents.get(id);
			
		} else if (this.registeredTeachers.containsKey(id)) { // Check if a teacher exists with the given ID
		
			loginUser = this.registeredTeachers.get(id);
		
		} else {
			
			return false; // Didn't find student with received ID
		}
		
		// Check password
		if (loginUser.getPassword().equals(password)) {
			
			if (loginUser.getClass().equals(Student.class)) {
			
				this.isStudentLoggedIn = true;
			    this.isAdminLoggedIn = false;
			    this.isTeacherLoggedIn = false;
			
			} else if (loginUser.getClass().equals(Teacher.class)) {
			
				this.isTeacherLoggedIn = true;
				this.isAdminLoggedIn = false;
				this.isStudentLoggedIn = false;
			}
			
			this.loggedInUser = loginUser;
			
			return true;
			
		} else {
			
			// Here we can add extra wrong password logic,
			// such as counting failed attempts
			return false;
		}
	}
	
	public void logout() {
		
		this.isAdminLoggedIn = false;
		this.isStudentLoggedIn = false;
		this.isTeacherLoggedIn = false;
		this.loggedInUser = null;
	}
	
	public boolean isStudentLoggedIn() {
		
		return this.isStudentLoggedIn;
	}
	
	public boolean isAdminLoggedIn() {
		
		return this.isAdminLoggedIn;
	}
	
	public boolean isTeacherLoggedIn() {
		
		return this.isTeacherLoggedIn;
	}
	
	public User getLoggedInUser() {
		
		if (this.loggedInUser.getClass().equals(Student.class)) {
			
			return (User) new Student((Student) this.loggedInUser);
		
		} else if (this.loggedInUser.getClass().equals(Teacher.class)) {
		
			return (User) new Teacher((Teacher) this.loggedInUser);
		
		} else {
			
			return null;
		}
	}
}
