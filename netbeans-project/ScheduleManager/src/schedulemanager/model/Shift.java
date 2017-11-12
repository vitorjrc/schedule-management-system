package schedulemanager.model;

import java.util.HashSet;

/**
 * Represents a shift, which belongs to a course, which in turn belongs to a major.
 */
public class Shift {

    private String id;           // The ID of this shift. Example: "PL1"
    private String courseId;     // The ID of the course this shift belongs to. Example: "DSS"
    private int occupationLimit; // The maximum number of students allowed in this shift  
    private HashSet<Student> occupants; // The students that frequent this shift

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    
    public int getOccupationLimit() {
        return occupationLimit;
    }

    public void setOccupationLimit(int occupationLimit) {
        this.occupationLimit = occupationLimit;
    }

    public HashSet<Student> getOccupants() {
        return occupants;
    }

    public void setOccupants(HashSet<Student> occupants) {
        this.occupants = occupants;
    }
}
