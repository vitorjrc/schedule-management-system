package schedulemanager.model;

/**
 * Class that represents a Course
 */

import java.util.*;
import java.io.Serializable;

public class Course implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    private String id;
    private String name;
    
    private HashMap<String, Shift> shifts; // Shift ID -> Shift
    
    // Constructors
    
    public Course(String name) {
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

        shifts.put(id, shift);
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
