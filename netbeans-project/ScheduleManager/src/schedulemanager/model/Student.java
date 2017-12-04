package schedulemanager.model;

/**
 * Class that represents a student
 */

import java.util.*;
import java.io.Serializable;
import schedulemanager.db.*;

public class Student extends User implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;              
    
    private StudentRegimen regimen;
    // Shifts this user is enrolled in (CourseID -> (ShiftID -> Shift))
    private CoursesByStudentDAO coursesByStudentDAO;
    
    public Student(String id, String name, String password, String regimen) {
    	
    	super(id, name, password);
        
        if (regimen.toLowerCase().equals("student")) {
        	
        	this.regimen = StudentRegimen.STUDENT;
        	
        } else if (regimen.toLowerCase().equals("workerstudent")) {
        	
        	this.regimen = StudentRegimen.WORKERSTUDENT;
        
        } else {
        	throw new java.lang.RuntimeException("Tried to create student with unknown regimen");
        }
        
        coursesByStudentDAO = new CoursesByStudentDAO(); // Starts out empty
    }
    
    public Student(Student s){
    	
    	super(s);

        this.regimen = StudentRegimen.valueOf(s.getRegimen());
        coursesByStudentDAO = new CoursesByStudentDAO();
        coursesByStudentDAO.putAll(s.getShiftsByCourse());
        
    }
    
    public String getRegimen() { return this.regimen.name(); }
    
    // Returns de student's shifts in the format CourseID -> (ShiftID -> Shift)
    public CoursesByStudentDAO getShiftsByCourse() { 
        return coursesByStudentDAO;
    }
    
    public ArrayList<String> getCourses() {
        
        return new ArrayList<String>(coursesByStudentDAO.keySet());
    }
 
    // === Methods
    
    // Wether a student can swap directly, without a taker
    public boolean canDirectSwap() {
    	return this.regimen == StudentRegimen.WORKERSTUDENT;
    }
    
    // Enrolls this student in a shift
    public void assignShift(Shift shift) {

    	// Check if HashMap of this course's shifts exists
        if (!coursesByStudentDAO.containsKey(shift.getCourseId())) {
            coursesByStudentDAO.put(shift.getCourseId(), new ShiftDAO());
    	}
        
        // Add this shift to the right course on the shifts HashMap
        coursesByStudentDAO.get(shift.getCourseId()).put(shift.getId(), shift);
        
        // Add this student to the occupant list on the shift
        shift.addOccupant(this);
    }
    
    public void removeFromShift(Shift shift) {
    	
    	String courseID = shift.getCourseId();
    	
    	if (coursesByStudentDAO.containsKey(courseID)) {
    		coursesByStudentDAO.get(courseID).remove(shift.getId());
    	}
    }
    
    public boolean hasShift(String courseID, String shiftID) {
    	
    	return coursesByStudentDAO.containsKey(courseID) && coursesByStudentDAO.get(courseID).containsKey(shiftID);
    }
    
    public void setShifts(ArrayList<String> novo) {
        for(String s: novo) {
            coursesByStudentDAO.put(s, new ShiftDAO());
        }
    }
    
    // === /Methods
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o==null || o.getClass() != this.getClass()) return false;
        Student s = (Student) o;
        return ( this.name.equals(s.getName()) &&
                 this.id.equals(s.getID()) &&
                 this.password.equals(s.getPassword()) &&
                 this.regimen.toString().equals(s.getRegimen()) &&
                 coursesByStudentDAO.equals(s.getShiftsByCourse())
        );
    }
    
    public String toString() {
    	
        StringBuilder s = new StringBuilder();
        s.append("----------ALUNO----------\n");
        s.append("NOME: " + this.name + "\n");
        s.append("ID: " + this.id + "\n");
        s.append("PASSWORD: " + this.password + "\n");
        s.append("REGIME: " + this.regimen.toString() + "\n");
        s.append("Enrolled in the following shifts: \n");
        
        for (String course: coursesByStudentDAO.keySet()) {
            
        	s.append("  " + course + ":\n");
            
            for (String shift: coursesByStudentDAO.get(course).keySet()) {

            	s.append("  " + shift + "\n");
            }
        }
        
        s.append("-------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Student clone(){
        return new Student(this);
    }
}