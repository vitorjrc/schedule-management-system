package schedulemanager.model;

import java.util.*;
import schedulemanager.db.*;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */
public class Model {
    
    private AuthManager authManager;
    private SwapManager swapManager;
    private IO IO;
    private CourseDAO courseDAO;

    public Model() {
        this.authManager = new AuthManager();
        this.swapManager = new SwapManager(authManager);
        this.IO = new IO(this);
        this.courseDAO = new CourseDAO();
    }
    
    public LinkedHashMap<String, Course> getCourses() {
        
        LinkedHashMap<String, Course> ret = new LinkedHashMap<String, Course>();
        for(Course c : this.courseDAO.values())
            ret.put(c.getId(), c.clone());
        
        return ret;
    }
    
    public Course createCourse(String courseID, String courseName, String teacherID) {
    	
    	Course newCourse = new Course(courseID, courseName, teacherID);
    	
    	this.courseDAO.put(courseID, newCourse);
    	
    	return newCourse;
    }
    
    public Shift createShift(String shiftID, String courseID, int occupationLimit, String teacher, String classroom) {
    	
    	if (!this.courseDAO.containsKey(courseID)) {
    		
    		return null; // Tried to create shift of a course that doesn't exist
    	}
    	
    	Shift newShift = new Shift(shiftID, courseID, occupationLimit, teacher, classroom);
    	
    	// Add shift to its course
    	this.courseDAO.get(courseID).addShift(shiftID, newShift);
    	
    	return newShift;
    }
    
    public Student registerStudent(String id, String name, String password, String regimen) {
        
        return this.authManager.registerStudent(id, name, password, regimen);
    }
    
    public Teacher registerTeacher(String id, String name, String password) {
    	
    	return this.authManager.registerTeacher(id, name, password);
    }
    
    public void assignTeacherToCourse(String teacherID, String course) {
        
        this.authManager.assignTeacherToCourse(teacherID, course);
    }
    
    public String login(String id, String password) {
    	
    	return this.authManager.login(id, password);
    }
    
    public void logout() {
    	
    	this.authManager.logout();
    }
    
    public boolean isStudentLoggedIn() {
    	return this.authManager.isStudentLoggedIn();
    }
    
    public boolean isTeacherLoggedIn() {
    	return this.authManager.isTeacherLoggedIn();
    }
    
    public boolean isAdminLoggedIn() {
    	return this.authManager.isAdminLoggedIn();
    }
    
    public boolean directSwap(String studentID, String courseID, String fromShiftID, String toShiftID) {
    	
    	return this.swapManager.directSwap(studentID, courseID, fromShiftID, toShiftID, (CourseDAO) this.courseDAO);
    }
    
    public User getLoggedInUser() {
    	return this.authManager.getLoggedInUser();
    }
    
    public boolean createSwapOffer(String bidderID, String courseID, String offeredShiftID, String wantedShiftID) {
    	
    	return this.swapManager.createSwapOffer(bidderID, courseID, offeredShiftID, wantedShiftID);
    }
    
    public boolean cancelSwapOffer(String studentID, String swapID) {
    	
    	return this.swapManager.cancelSwapOffer(studentID, swapID);
    }
    
    public boolean takeSwapOffer(String takerID, String swapID) {
    	
    	return this.swapManager.takeSwapOffer(takerID, swapID, this.courseDAO);
    }
    
    public boolean isSwapTakeable(String takerID, Swap swap) {
    	
    	return this.swapManager.isSwapTakeable(takerID, swap);
    }
    
    public boolean isSwapTakeable(String takerID, String swapID) {
    	
    	Swap swap = this.swapManager.getOpenSwaps().get(swapID).clone();
    	
    	if (swap == null) {
    		
    		System.out.println("Swap not takeable: could not find open swap with ID " + swapID + "\n");
    		return false;
    	}
    	
    	return this.swapManager.isSwapTakeable(takerID, swap);
    }
    
    public void lockSwaps() {
    	
    	this.swapManager.lockSwaps();
    }
    
    public void unlockSwaps() {
    	
    	this.swapManager.unlockSwaps();
    }
    
    public HashMap<String, Swap> getAllSwaps() {
    	
    	return this.swapManager.getAllSwaps();
    }
    
    public HashMap<String, Swap> getAllSwapsOfStudent(String studentID) {
    	
    	return this.swapManager.getAllSwapsOfStudent(studentID);
    }
    
    public HashMap<String, Swap> getOpenSwaps() {

        return this.swapManager.getOpenSwaps();
	}
    
    public HashMap<String, Swap> getClosedSwaps() {
        
    	return this.swapManager.getClosedSwaps();
    }
    
    public HashMap<String, Swap> getOpenSwapsOfStudent(String studentID) {
    	
    	return this.swapManager.getOpenSwapsOfStudent(studentID);
	}
    
    public HashMap<String, Swap> getClosedSwapsOfStudent(String studentID) {
    	
    	return this.swapManager.getClosedSwapsOfStudent(studentID);
    }
    
    public void save() {
        this.IO.save();
    }
    
    public void load() {
        this.IO.load();
    }
    
    public Student getLoggedinStudent(String id) {
        
        return this.authManager.getStudentByID(id);
    }
    
    public void setSwaps(HashMap<String, HashMap<String, Swap>> newMap) {
        
        this.swapManager.setSwaps(newMap);
    }
    /*
    public void setStudents(HashMap<String, Student> newMap) {
        
        this.authManager.setStudents(newMap);
    }
        
    public void setTeachers(HashMap<String, Teacher> newMap) {
        
        this.authManager.setTeachers(newMap);
    }

    
    public void setCourses(LinkedHashMap<String, Course> newMap) {
        
        this.courseDAO = newMap;
    }
*/
    
    public StudentDAO getStudents() {
        
        return this.authManager.getRegisteredStudents();
    }
    
    public TeacherDAO getTeachers() {
        
        return this.authManager.getRegisteredTeachers();
    }
    
    public HashMap<String, HashMap<String, Swap>> getSwapsByStudentID() {
        
        return this.swapManager.getSwapsByStudentID();
    }
    
}
