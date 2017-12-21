package schedulemanager.model;

import java.io.Serializable;
import schedulemanager.db.TeacherDAO;
import schedulemanager.db.StudentDAO;

/**
 * Manages authentication stuff
 */
public class AuthManager implements Serializable{
	
	private boolean isStudentLoggedIn = false;
	private boolean isAdminLoggedIn = false;
	private boolean isTeacherLoggedIn = false;
	private User loggedInUser = null;
    private static final long serialVersionUID = 7526472295622776147L;
    private StudentDAO studentDAO;
    private TeacherDAO teacherDAO;
        
	
	//private HashMap<String, Student> studentDAO; // StudentID -> Student
	//private HashMap<String, Teacher> teacherDAO; // TeacherID -> Teacher
	
	// Admin login credentials. Not safe in current form.
	private static final String adminID = "admin";
	private static final String adminPassword = "SECURITYHOLE";
	
	public AuthManager() {
		
            this.studentDAO = new StudentDAO();
            this.teacherDAO = new TeacherDAO();
	
        }
	
	public Student registerStudent(String id, String name, String password, String regimen) {
		
		Student newStudent = new Student(id, name, password, regimen);
		
		studentDAO.put(id, newStudent);
		
		return newStudent;
	}
	
	// Register a Teacher.
	// courseManagedID is the ID of the course the teacher manages.
	public Teacher registerTeacher(String id, String name, String password) {
		
		Teacher newTeacher = new Teacher(id, name, password);
		
		teacherDAO.put(id, newTeacher);
		
		return newTeacher;
	}
	
	public Student getStudentByID(String id) {
		
		return this.studentDAO.get(id);
	}
	
	public Teacher getTeacherByID(String id) {
		
		return this.teacherDAO.get(id);
	}
	
	// Returns null if login was successful, error message otherwise
	public String login(String id, String password) {
		
		// Check if this is an admin login attempt
		if (id.equals(AuthManager.adminID)) {
			if (password.equals(AuthManager.adminPassword)) {
				
				this.isAdminLoggedIn = true;
				this.isStudentLoggedIn = false;
				this.loggedInUser = null;
				
				return null;
			
			} else {
				
				return "Password errada (Login de Administrador)";
			}
		}
		
		User loginUser;
		
		// Check if a student exists with the given ID
		if (this.studentDAO.containsKey(id)) {
			
			loginUser = this.studentDAO.get(id);
			
		} else if (this.teacherDAO.containsKey(id)) { // Check if a teacher exists with the given ID
		
			loginUser = this.teacherDAO.get(id);
		
		} else {
			
			return "Não existe nenhum aluno/docente com a ID recebida";
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
			
			return null;
			
		} else {
			
			// Here we can add extra wrong password logic,
			// such as counting failed attempts
			return "Password errada";
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
        
	public StudentDAO getRegisteredStudents() {
            
		return studentDAO;
	}
        
	public TeacherDAO getRegisteredTeachers() {
            
        return teacherDAO;
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
        
	public void assignTeacherToCourse(String teacherID, String courseID) {
            
		this.teacherDAO.get(teacherID).setManagedCourseID(courseID);
	}
}
