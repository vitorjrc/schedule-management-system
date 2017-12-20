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
    private ShiftDAO shiftDAO;
    
    public Student(String id, String name, String password, String regimen) {
    	
    	super(id, name, password);
        
        if (regimen.toLowerCase().equals("student")) {
        	
        	this.regimen = StudentRegimen.STUDENT;
        	
        } else if (regimen.toLowerCase().equals("workerstudent")) {
        	
        	this.regimen = StudentRegimen.WORKERSTUDENT;
        
        } else {
        	throw new java.lang.RuntimeException("Tried to create student with unknown regimen");
        }
        
        this.shiftDAO = new ShiftDAO();
    }
    
    public Student(Student s){
    	
    	super(s);

        this.regimen = StudentRegimen.valueOf(s.getRegimen());
        this.shiftDAO = new ShiftDAO();
        
        this.setShifts(s.getShifts());
    }
    
    public String getRegimen() { return this.regimen.name(); }
    
    // Returns the student's shifts
    public Collection<Shift> getShifts() { 
        return this.shiftDAO.getShiftsOfStudent(this.getID());
    }
    
    //public ArrayList<String> getCourses() {
        
    //    return new ArrayList<String>(coursesByStudentDAO.keySet());
   // }
 
    // Whether a student can swap directly, without a taker
    public boolean canDirectSwap() {
    	return this.regimen == StudentRegimen.WORKERSTUDENT;
    }
    
    // Enrolls this student in a shift
    public void assignShift(Shift shift) {

    	// Check if HashMap of this course's shifts exists
        if (!this.shiftDAO.isStudentInShift(this.getID(), shift.getCourseID())) {
            this.shiftDAO.assignStudentToShift(this.getID(), shift.getID());
    	}
    }
    
    public void removeFromShift(Shift shift) {
    	
    	this.shiftDAO.removeStudentFromShift(this.getID(), shift.getID());
    }
    
    public boolean hasShift(String shiftID) {
    	
    	return this.shiftDAO.isStudentInShift(this.getID(), shiftID);
    }
    
    public void setShifts(Collection<Shift> shifts) {
    	
    	for (Shift s : shifts) {
    		
    		this.shiftDAO.assignStudentToShift(this.getID(), s.getID());
    	}
    }
    
    public void setShifts(ArrayList<String> shifts) {
    	
        for(String s: shifts) {
            this.shiftDAO.assignStudentToShift(this.getID(), s);
        }
    }
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o==null || o.getClass() != this.getClass()) return false;
        Student s = (Student) o;
        return ( this.name.equals(s.getName()) &&
                 this.id.equals(s.getID()) &&
                 this.password.equals(s.getPassword()) &&
                 this.regimen.toString().equals(s.getRegimen())
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
        
        for (Shift shift : this.shiftDAO.getShiftsOfStudent(this.getID())) {
            
        	s.append("  " + shift.getID() + ":\n");
        }
        
        s.append("-------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Student clone(){
        return new Student(this);
    }
}