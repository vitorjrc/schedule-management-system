package schedulemanager.model;

import java.util.HashSet;
import java.util.Iterator;
import java.io.Serializable;

/**
 * Represents a shift, which belongs to a course, which in turn belongs to a major.
 */
public class Shift implements Serializable {
    
    private static final long serialVersionUID = 7526472295622776147L;
    private String id;           // The ID of this shift. Example: "PL1"
    private String courseId;     // The ID of the course this shift belongs to. Example: "DSS"
    private int occupationLimit; // The maximum number of students allowed in this shift  
    private String teacher;
    private String classroom;
    private HashSet<Student> occupants; // The students that frequent this shift
    
     public Shift(String id, String courseId, int occupationLimit, String teacher, String classroom) {
        this.id = id;
        this.courseId = courseId;
        this.occupationLimit = occupationLimit;
        this.teacher = teacher;
        this.classroom = classroom;
        this.occupants = new HashSet<Student>();
    }
    
    public Shift(Shift s) {
        this.id = s.getId();
        this.courseId = s.getCourseId();
        this.occupationLimit = s.getOccupationLimit();
        this.teacher = s.getTeacher();
        this.classroom = s.getClassroom();
        this.occupants = new HashSet<Student>();
        
        Iterator<Student> iterator = occupants.iterator();
        while(iterator.hasNext()){
            this.occupants.add(iterator.next().clone());
        }
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getCourseId() {
        return this.courseId;
    }
    
    public void setCourseId(String id) {
        this.courseId = id;
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

    public HashSet<Student> getOccupants() {
        return this.occupants;
    }
   
    
    public String toString() {
        
        return ("UC: " + this.courseId + " Turno: " + this.id);
    }
    
    @Override
    public Shift clone() {
        return new Shift(this);
    }
}
