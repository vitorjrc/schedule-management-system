package schedulemanager.model;

/**
 * Class that represents a student
 */

import java.util.*;
import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    private String name;             
    private String id;                  
    private String password;                  
    private StudentRegimen regimen;
    private HashMap<String, HashMap<String, Shift>> shifts; // Shifts this user is enrolled in (CourseID -> (ShiftID -> Shift))
    
    public Student(String id, String name, String pass, String regimen) {
        this.name = name;
        this.id = id;
        this.password = pass;
        
        if (regimen.toLowerCase().equals("student")) {
        	
        	this.regimen = StudentRegimen.STUDENT;
        	
        } else if (regimen.toLowerCase().equals("workerstudent")) {
        	
        	this.regimen = StudentRegimen.WORKERSTUDENT;
        
        } else {
        	throw new java.lang.RuntimeException("Tried to create student with unknown regimen");
        }
        
        this.shifts = new HashMap<String, HashMap<String, Shift>>(); // Starts out empty
/*
        for (String courseID: courseIDs)
           this.shifts.put(courseID, new HashMap<String, Shift>());
    }
*/
    }  
    public Student(Student s){
        this.name = s.getName();
        this.id = s.getID();
        this.password = s.getPassword();
        this.regimen = StudentRegimen.valueOf(s.getRegimen());
        this.shifts = new HashMap<String, HashMap<String, Shift>>(s.shifts); // Shallow clone - good enough
    }

    public String getName() { return this.name; }
    
    public String getID() { return this.id; }
    
    public String getPassword() { return this.password; }
    
    public String getRegimen() { return this.regimen.name(); }
    
    public HashMap<String, HashMap<String, Shift>> getShifts() { return new HashMap<String, HashMap<String, Shift>>(this.shifts); }
    

    public HashMap<String, String> getShiftsString() {
        
        // UC e turno
        HashMap<String, String> shiftsList = new HashMap<String, String>();
        for(Map.Entry<String, HashMap<String, Shift>> entry: shifts.entrySet()) {
            for(String s: entry.getValue().keySet())
                shiftsList.put(entry.getKey(), s);
        }
        return shiftsList;
    }

    public ArrayList<String> getUCsString() {
        
        ArrayList<String> strings = new ArrayList<String>(shifts.keySet());
        return strings;
    }
    
    // === Methods
    
    // Wether a student can swap directly, without a taker
    public boolean canDirectSwap() {
    	return this.regimen == StudentRegimen.WORKERSTUDENT;
    }
    
    // Enrolls this student in a shift
    public void assignShift(Shift shift) {

    	// Check if HashMap of this course's shifts exists
        if (!this.shifts.containsKey(shift.getCourseId())) {
            this.shifts.put(shift.getCourseId(), new HashMap<String, Shift>());
    	}
        
        // Add this shift to the right course on the shifts HashMap
        this.shifts.get(shift.getCourseId()).put(shift.getId(), shift);
        
    }
    
    public void removeFromShift(Shift shift) {
    	
    	String courseID = shift.getCourseId();
    	
    	if (this.shifts.containsKey(courseID)) {
    		this.shifts.get(courseID).remove(shift.getId());
    	}
    }
    
    public boolean hasShift(String courseID, String shiftID) {
    	
    	return this.shifts.containsKey(courseID) && this.shifts.get(courseID).containsKey(shiftID);
    }
    
    public void setShifts(ArrayList<String> novo) {
        for(String s: novo) {
            this.shifts.put(s, new HashMap<String, Shift>());
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
                 this.shifts.equals(s.getShifts())
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
        
        for (String course: this.shifts.keySet()) {
            
        	s.append("  " + course + ":\n");
            
            for (String shift: this.shifts.get(course).keySet()) {

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