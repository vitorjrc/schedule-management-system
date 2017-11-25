package schedulemanager.model;

import java.util.*;
import java.io.Serializable;

/**
 * Represents a course, which has shifts, which in turn have enrolled students.
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    private String id;
    private String name;
    
    private HashMap<String, Shift> shifts; // Shift ID -> Shift
    
    // Constructors
    
    public Course(String id, String name) {
        this.id = id;
        this.name = name;
        this.shifts = new HashMap<String, Shift>();
    }
    
    public Course(Course s) {
        this.name = s.getName();
        this.shifts = new HashMap<String, Shift>();
        for (Map.Entry<String, Shift> entry : getShifts().entrySet()){
            shifts.put(entry.getKey(),entry.getValue());
        }
    }
    
    // Getters
    
    public String getId() { 
        return this.id; 
    }
    
    public String getName() { 
        return this.name; 
    }
    
    public HashMap<String, Shift> getShifts() { 
        return this.shifts; 
    }
    
    // Attaches a previously created shift to this course
    public void addShift(String id, Shift shift) {
    	
    	shift.setCourseId(this.id);

        this.shifts.put(id, shift);
    }
    
    public void removeShift(String id) {
    	this.shifts.remove(id);
    }
    
    public Shift Shift0() {

        Shift newt = new Shift("PL0", this.id);
        this.shifts.put("PL0", newt);
        return newt;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.getName());
        return s.toString();
    }
    
    @Override
    public Course clone() {
        return new Course(this);
    }
}
