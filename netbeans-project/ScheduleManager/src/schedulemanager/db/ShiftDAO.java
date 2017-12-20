package schedulemanager.db;

import schedulemanager.db.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.model.Shift;

public class ShiftDAO implements Map<String, Shift> {
    
    private Connection conn;
    
    /**
     * Delete all shifts
     */
    @Override
    public void clear() {

        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM shift");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Checks if a shift with a given ID exists in the database
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT id FROM shift WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            r = rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    /**
     * Checks if a shift exists in the database 
     * 
     * Provisional implementation - should check entire object and not just key
     */
    @Override
    public boolean containsValue(Object value) {
        Shift s = (Shift) value;
        return containsKey(s.getID());
    }
    
    /**
     * Return the set of entries
     */
    @Override
    public Set<Map.Entry<String,Shift>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
    
    /**
     * Equals
     */    
    @Override
    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }
    
    /**
     * Return a shift with a given ID
     */
    @Override
    public Shift get(Object key) {
        Shift t = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM shift WHERE id = ?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                t = new Shift(
                	rs.getString("id"),
                	rs.getString("course_id"),
                	rs.getInt("occupation_limit"),
                	rs.getString("teacher"),
                	rs.getString("classroom")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return t;
    }
    
    
    /**
     * hashCode
     */
    @Override
    public int hashCode() {
        return this.conn.hashCode();
    }
    
    /**
     * Check if database table is empty
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    
    /**
     * keySet
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }
    
    /**
     * Insert a shift into the database
     */
    @Override
    public Shift put(String key, Shift value) {
    	
    	// From Java docs:
        // Returns:
        // the previous value associated with key, or null if there was no mapping for key.
        // (A null return can also indicate that the map previously associated null with key,
        // if the implementation supports null values.)
    	
    	Shift previous = this.get(key);
    	
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO shift\n" +
                "VALUES (?, ?, ?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, String.valueOf(value.getOccupationLimit()));
            stm.setString(3, value.getTeacher());
            stm.setString(4, value.getClassroom());
            stm.setString(5, value.getCourseID());

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }

    /**
     * Insert a set of shifts into the database
     */
    @Override
    public void putAll(Map<? extends String, ? extends Shift> t) {
        for(Shift s : t.values()) {
            this.put(s.getID(), s);
        }
    }
    
    /**
     * Remove a shift given their ID
     */
    @Override
    public Shift remove(Object key) {
    	
        Shift previous = this.get(key);
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM shift WHERE id = ?");
            stm.setString(1, (String) key);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }
    
    /**
     * Return the number of shift rows in the database
     */
    @Override
    public int size() {
    	
        int i = 0;
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM shift");
            if (rs.next()) {
                i = rs.getInt(1);
            }
        
        } catch (Exception e) {
        	throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return i;
    }
    
    /**
     * Get all shifts in the database
     */
    @Override
    public Collection<Shift> values() {
    	
        Collection<Shift> col = new HashSet<Shift>();
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM shift");
            while (rs.next()) {
                col.add(new Shift(
                	rs.getString("id"),
                	rs.getString("course_id"),
                	rs.getInt("occupation_limit"),
                	rs.getString("teacher"),
                	rs.getString("classroom")
                ));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
    /**
     * Get all shifts of a course
     */
    public Collection<Shift> getShiftsOfCourse(String courseID) {
    	
    	Collection<Shift> col = new HashSet<Shift>();
        
        try {
            conn = Connect.connect();

            PreparedStatement stm = conn.prepareStatement("SELECT * FROM shift WHERE course_id = ?");
            stm.setString(1, courseID);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                col.add(new Shift(
                	rs.getString("id"),
                	rs.getString("course_id"),
                	rs.getInt("occupation_limit"),
                	rs.getString("teacher"),
                	rs.getString("classroom")
                ));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
    /**
     * Get all shifts of a student
     */
    public Collection<Shift> getShiftsOfStudent(String studentID) {
    	
    	Collection<Shift> col = new HashSet<Shift>();
        
        try {
            conn = Connect.connect();

            PreparedStatement stm = conn.prepareStatement("SELECT * FROM student_shift WHERE student_id = ?");
            stm.setString(1, studentID);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
            	
            	Shift s = this.get(rs.getString("shift_id"));
            	
                col.add(s);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    	
    }
    
    /**
     * Add a student to a shift
     */
    public void assignStudentToShift(String studentID, String shiftID) {
    	
    	try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO student_shift\n" +
                "VALUES (?, ?)");
            stm.setString(1, studentID);
            stm.setString(2, shiftID);

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Remove a student from a shift
     * 
     * Returns true if the removal was successful, false otherwise
     */
    public boolean removeStudentFromShift(String studentID, String shiftID) {
    	
    	if (!this.isStudentInShift(studentID, shiftID)) {
    		return false;
    	}
    	
    	try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM student_shift WHERE student_id = ? AND shift_id = ?");
            stm.setString(1, studentID);
            stm.setString(2, shiftID);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
    	
    	return true;
    }
    
    /**
     * Check if a student is in a certain shift
     */
    public boolean isStudentInShift(String studentID, String shiftID) {
    	
    	boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT student_id FROM student_shift WHERE student_id = ? AND shift_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, studentID);
            stm.setString(2, shiftID);
            ResultSet rs = stm.executeQuery();
            r = rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return r;
    }
}
