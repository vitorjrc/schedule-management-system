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
    private ArrayList<Shift> shifts; // Shifts this user is enrolled in
    
    // IDs of courses user is enrolled in
    // Doesn't necessarily mean they're enrolled in a shift of that course
    private ArrayList<String> courseIDs; 
    
    public Student(String id, String name, String pass, String regimen, ArrayList<String> courseIDs) {
        this.name = name;
        this.id = id;
        this.password = pass;
        
        if (regimen.toLowerCase() == "student") {
        	
        	this.regimen = StudentRegimen.STUDENT;
        	
        } else if (regimen.toLowerCase() == "workerstudent") {
        	
        	this.regimen = StudentRegimen.WORKERSTUDENT;
        
        } else {
        	throw new java.lang.RuntimeException("Tried to create student with unknown regimen");
        }
        
        this.shifts = new ArrayList<Shift>(); // Starts out empty
        
        this.courseIDs = new ArrayList<String>();
        for (String s: courseIDs)
           this.courseIDs.add(s);
    }
    
    public Student(Student s){
        this.name = s.getName();
        this.id = s.getID();
        this.password = s.getPassword();
        this.regimen = StudentRegimen.valueOf(s.getRegimen());
        
        this.shifts = new ArrayList<Shift>();
        for (Shift st: s.getShifts()) {
            this.shifts.add(st.clone());
        }
        
        this.courseIDs = new ArrayList<String>();
        for (String courseID: s.getCourseIDs()) {
            this.courseIDs.add(courseID);
        }
    }

    public String getName() { return this.name; }
    
    public String getID() { return this.id; }
    
    public String getPassword() { return this.password; }
    
    public String getRegimen() { return this.regimen.name(); }
    
    public ArrayList<Shift> getShifts() { return new ArrayList<Shift>(this.shifts); }
    
    public ArrayList<String> getShiftsString() {
        
        ArrayList<String> strings = new ArrayList<String>();
        for(Shift s: shifts) {
            strings.add(s.toString());
        }
        
        return strings;
    }
    
    public void setShifts(ArrayList<Shift> shifts) {
        this.shifts = shifts;
    }
    
    public ArrayList<String> getCourseIDs() { return new ArrayList<String>(this.courseIDs); }
    
    public ArrayList<String> getUCsString() {
        ArrayList<String> strings = new ArrayList<String>();
        
        for(Shift s: shifts) {
            strings.add(s.getCourseId());
        }
        
        return strings;
    }
    
    // === Methods
    
    // Enrolls this student in a shift
    public void assignShift(Shift shift) {

    	this.shifts.add(shift.clone());
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
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----------ALUNO----------\n");
        s.append("NOME: " + this.name + "\n");
        s.append("ID: " + this.id + "\n");
        s.append("PASSWORD: " + this.password + "\n");
        s.append("REGIME: " + this.regimen.toString() + "\n");
        s.append("Enrolled in the following shifts: \n");
        for(Shift st : this.shifts)
            s.append(st.toString() + "\n");
        s.append("-------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Student clone(){
        return new Student(this);
    }
}