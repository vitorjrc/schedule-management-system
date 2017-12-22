package schedulemanager.model;

import java.util.HashMap;
import java.io.Serializable;

/**
 * Represents a shift, which belongs to a course, which in turn belongs to a major.
 */
public class Shift implements Serializable {
    
    private static final long serialVersionUID = 7526472295622776147L;
    private String id;           // The ID of this shift. Example: "PL1"
    private String courseID;     // The ID of the course this shift belongs to. Example: "DSS"
    private int occupationLimit; // The maximum number of students allowed in this shift  
    private String teacher;
    private String classroom;
    private HashMap<String, Student> occupants; // The students that frequent this shift - StudentID -> Student
    
     public Shift(String id, String courseID, int occupationLimit, String teacher, String classroom) {
        this.id = id;
        this.courseID = courseID;
        this.occupationLimit = occupationLimit;
        this.teacher = teacher;
        this.classroom = classroom;
        this.occupants = new HashMap<String, Student>();
    }
    
    public Shift(Shift s) {
        this.id = s.getID();
        this.courseID = s.getCourseID();
        this.occupationLimit = s.getOccupationLimit();
        this.teacher = s.getTeacher();
        this.classroom = s.getClassroom();
        this.occupants = new HashMap<String, Student>(s.getOccupants());
    }
    
    public String getID() {
        return this.courseID + ":" + this.id;
    }
    
    public String getCourseID() {
        return this.courseID;
    }
    
    public void setCourseID(String id) {
        this.courseID = id;
    }
    
    public int getOccupationLimit() {
        return this.occupationLimit;
    }
    
    public String getTeacher() {
        return this.teacher;
    }
    
    public String getClassroom(){
        return this.classroom;
    }

    public HashMap<String, Student> getOccupants() {
        return new HashMap<String, Student>(this.occupants);
    }
    
    public void addOccupant(Student s) {
        
    	this.occupants.put(s.getID(), new Student(s));
    }
   
    
    public String toString() {

        return ("UC: " + this.courseID + " Turno: " + this.id);
    }
    
    @Override
    public Shift clone() {
        return new Shift(this);
    }
}
