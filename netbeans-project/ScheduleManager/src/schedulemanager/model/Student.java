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
   
    public Student() {
        this.name = "Alberto Caeiro";
        this.id = "A00000";
        this.password = "heterocromatico";
        this.regimen = StudentRegimen.STUDENT;
        this.shifts = new ArrayList<Shift>();
    }
    
    public Student(String name, String id, String pass, String regimen, ArrayList<Shift> shifts) {
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
        
        this.shifts = new ArrayList<Shift>();
        for (Shift s: shifts)
           this.shifts.add(s);
    }
    
    public Student(Student s){
        this.name = s.getName();
        this.id = s.getID();
        this.password = s.getPassword();
        this.regimen = StudentRegimen.valueOf(s.getRegimen());
        this.shifts = new ArrayList<>();
        for (Shift st: s.getShifts()) {
            this.shifts.add(st); // Strings are immutable, no need to clone()
        }
    }

    public String getName() { return this.name; }
    
    public String getID() { return this.id; }
    
    public String getPassword() { return this.password; }
    
    public String getRegimen() { return this.regimen.toString(); }
    
    public ArrayList<Shift> getShifts() { return new ArrayList<Shift>(this.shifts); }
    
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
