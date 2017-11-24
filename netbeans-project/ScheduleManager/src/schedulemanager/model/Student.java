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
    private String status;                       
    private ArrayList<String> shifts; // ID of shifts where this student is enrolled
   
    public Student() {
        this.name = "Alberto Caeiro";
        this.id = "A00000";
        this.password = "heterocromatico";
        this.status = "";
        this.shifts = new ArrayList<String>();
    }
    
    public Student(String name, String id, String pass, String stat, ArrayList<String> shifts) {
        this.name = name;
        this.id = id;
        this.password = pass;
        this.status = stat;
        this.shifts = new ArrayList<String>();
        for (String s: shifts)
           this.shifts.add(s);
    }
    
    public Student(Student s){
        this.name = s.getName();
        this.id = s.getID();
        this.password = s.getPassword();
        this.status = s.getStatus();
        this.shifts = new ArrayList<>();
        for (String st: s.getShifts()) {
            shifts.add(st); // Strings are immutable, no need to clone()
        }
    }

    public String getName() { return this.name; }
    
    public String getID() { return this.id; }
    
    public String getPassword() { return this.password; }
    
    public String getStatus() { return this.status; }
    
    public void setStatus(String stat){ this.status = stat; }
    
    public ArrayList<String> getShifts() { return this.shifts; }
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o==null || o.getClass() != this.getClass()) return false;
        Student s = (Student) o;
        return ( this.name.equals(s.getName()) &&
                 this.id.equals(s.getID()) &&
                 this.password.equals(s.getPassword()) &&
                 this.status.equals(s.getStatus()) &&
                 this.shifts.equals(s.getShifts())
        );
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----------ALUNO----------\n");
        s.append("NOME: " + this.name + "\n");
        s.append("ID: " + this.id + "\n");
        s.append("PASSWORD: " + this.password + "\n");
        s.append("REGIME: " + this.status + "\n");
        s.append("Enrolled in the following shifts: \n");
        for(String st : this.shifts)
            s.append(st + "\n");
        s.append("-------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Student clone(){
        return new Student(this);
    }
}
