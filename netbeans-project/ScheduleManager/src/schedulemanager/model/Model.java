package schedulemanager.model;

import java.util.*;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */
public class Model {
    
    private LinkedHashMap<String, Course> coursesList = new LinkedHashMap<String, Course>(); // Course ID -> Course
    private AuthManager authManager;
    private SwapManager swapManager;
    private IO IO;
	
    public Model() {
        this.authManager = new AuthManager();
        this.swapManager = new SwapManager(authManager);
        this.IO = new IO(this);
        //this.coursesList = new LinkedHashMap<String, Course>();
    }
    
    public LinkedHashMap<String, Course> getCourses() {
        
        LinkedHashMap<String, Course> ret = new LinkedHashMap<String, Course>();
        for(Course c : this.coursesList.values())
            ret.put(c.getId(), c.clone());
        
        return ret;
    }
    
    public Course createCourse(String courseID, String courseName, String teacherID) {
    	
    	Course newCourse = new Course(courseID, courseName, teacherID);
    	
    	this.coursesList.put(courseID, newCourse);
    	
    	return newCourse;
    }
    
    public Shift createShift(String shiftID, String courseID, int occupationLimit, String teacher, String classroom) {
    	
    	if (!this.coursesList.containsKey(courseID)) {
    		
    		return null; // Tried to create shift of a course that doesn't exist
    	}
    	
    	Shift newShift = new Shift(shiftID, courseID, occupationLimit, teacher, classroom);
    	
    	// Add shift to its course
    	this.coursesList.get(courseID).addShift(shiftID, newShift);
    	
    	return newShift;
    }
    
    public Student registerStudent(String id, String name, String password, String regimen) {
        
        return this.authManager.registerStudent(id, name, password, regimen);
    }
    
    public Teacher registerTeacher(String id, String name, String password, String managedCourseID) {
    	
    	return this.authManager.registerTeacher(id, name, password, managedCourseID);
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
    	
    	return this.swapManager.directSwap(studentID, courseID, fromShiftID, toShiftID, (HashMap<String, Course>) this.coursesList);
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
    	
    	return this.swapManager.takeSwapOffer(takerID, swapID, this.coursesList);
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
    
    public void setStudents(HashMap<String, Student> newMap) {
        
        this.authManager.setStudents(newMap);
    }
        
    public void setTeachers(HashMap<String, Teacher> newMap) {
        
        this.authManager.setTeachers(newMap);
    }
    
    public void setCourses(LinkedHashMap<String, Course> newMap) {
        
        this.coursesList = newMap;
    }
    
    public HashMap<String, Student> getStudents() {
        
        return this.authManager.getRegisteredStudents();
    }
    
    public HashMap<String, Teacher> getTeachers() {
        
        return this.authManager.getRegisteredTeachers();
    }
    
}
