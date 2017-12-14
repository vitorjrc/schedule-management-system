package schedulemanager.model;

import java.util.*;
import java.io.Serializable;
import schedulemanager.db.ShiftDAO;

/**
 * Represents a course, which has shifts, which in turn have enrolled students.
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    private String id;
    private String name;
    private String teacherID; // ID of teacher that runs this course
    private ShiftDAO shiftDAO;
    
    // Constructors
    public Course(String id, String name, String teacherID) {
        this.id = id;
        this.name = name;
        this.teacherID = teacherID;
        this.shiftDAO = new ShiftDAO();
    }
    
    public Course(Course c) {
        this.id = c.getID();
        this.name = c.getName();
        this.teacherID = c.getTeacherID();
        for (Shift s: c.getShifts().values()) {
            shiftDAO.put(s.getId(), s.clone());
        }
    }
    
    // Getters
    
    public String getID() { 
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
        for(Shift s : this.shiftDAO.values()){
            ret.put(s.getId(), s.clone());
        }
        
        return ret; 
    }
    
    public Shift getShift(String shiftID) {
    	// return this.shifts.get(shiftID).clone();
        return this.shiftDAO.get(shiftID);    
    }
    
    // Attaches a previously created shift to this course
    public void addShift(String id, Shift shift) {
    	
    	shift.setCourseId(this.id);

        this.shiftDAO.put(id, shift);
    }
    
    public void removeShift(String id) {
    	this.shiftDAO.remove(id);
    }
    
    
    public String toString() {
        return this.getName();
    }
    
    @Override
    public Course clone() {
        return new Course(this);
    }
}
