package schedulemanager.model;

/**
 * Represents a shift, which belongs to a course, which in turn belongs to a major.
 */
public class Shift {

    private int occupationLimit; // The maximum number of students allowed in this shift

    /**
     * @return the occupationLimit
     */
    public int getOccupationLimit() {
        return occupationLimit;
    }

    /**
     * @param occupationLimit the occupationLimit to set
     */
    public void setOccupationLimit(int occupationLimit) {
        this.occupationLimit = occupationLimit;
    }
}
