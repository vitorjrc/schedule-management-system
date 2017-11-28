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
    private String teacherID; // ID of teacher that runs this course
    private HashMap<String, Shift> shifts; // Shift ID -> Shift
    
    // Constructors
    public Course(String id, String name, String teacherID) {
        this.id = id;
        this.name = name;
        this.teacherID = teacherID;
        this.shifts = new HashMap<String, Shift>();
    }
    
    public Course(Course c) {
        this.id = c.getId();
        this.name = c.getName();
        this.teacherID = c.getTeacherID();
        this.shifts = new HashMap<String, Shift>();
        for (Shift s: c.getShifts().values()) {
            shifts.put(s.getId(), s.clone());
        }
    }
    
    // Getters
    
    public String getId() { 
        return this.id; 
    }
    
    public String getName() { 
        return this.name; 
    }
    
    public String getTeacherID() { 
        return this.teacherID; 
    }
    
    public HashMap<String, Shift> getShifts() { 
        HashMap<String, Shift> ret = new HashMap<String, Shift>();
        for(Shift s : this.shifts.values()){
            ret.put(s.getId(), s.clone());
        }
        
        return ret; 
    }
    
    public Shift getShift(String shiftID) {
    	// return this.shifts.get(shiftID).clone();
        return this.shifts.get(shiftID);    
    }
    
    // Attaches a previously created shift to this course
    public void addShift(String id, Shift shift) {
    	
    	shift.setCourseId(this.id);

        this.shifts.put(id, shift);
    }
    
    public void removeShift(String id) {
    	this.shifts.remove(id);
    }
    
    
    public String toString() {
        return this.getName();
    }
    
    @Override
    public Course clone() {
        return new Course(this);
    }
}
