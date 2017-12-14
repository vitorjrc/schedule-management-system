package schedulemanager.db;

import schedulemanager.db.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.model.Course;

public class CourseDAO implements Map<String, Course> {
    
    private Connection conn;
    
    /**
     * Delete all courses
     */
    @Override
    public void clear() {

        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM course");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Checks if a course with a given ID exists in the database
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT id FROM course WHERE id = ?";
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
     * Checks if a course exists in the database 
     * 
     * Provisional implementation - should check entire object and not just key
     */
    @Override
    public boolean containsValue(Object value) {
        Course s = (Course) value;
        return containsKey(s.getID());
    }
    
    /**
     * Return the set of entries
     */
    @Override
    public Set<Map.Entry<String,Course>> entrySet() {
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
     * Return a course with a given ID
     */
    @Override
    public Course get(Object key) {
        Course t = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM course WHERE id = ?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                t = new Course(rs.getString("id") ,rs.getString("name"), rs.getString("teacher_id"));
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
     * Insert a course in the database
     */
    @Override
    public Course put(String key, Course value) {
    	
    	// From Java docs:
        // Returns:
        // the previous value associated with key, or null if there was no mapping for key.
        // (A null return can also indicate that the map previously associated null with key,
        // if the implementation supports null values.)
    	
    	Course previous = this.get(key);
    	
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO course\n" +
                "VALUES (?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getName());
            stm.setString(3, value.getTeacherID());

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }

    /**
     * Insert a set of courses into the database
     */
    @Override
    public void putAll(Map<? extends String, ? extends Course> t) {
        for(Course s : t.values()) {
            this.put(s.getID(), s);
        }
    }
    
    /**
     * Remove a course given their ID
     */
    @Override
    public Course remove(Object key) {
    	
        Course previous = this.get(key);
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from course where id = ?");
            stm.setInt(1, (Integer) key);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }
    
    /**
     * Return the number of course rows in the database
     */
    @Override
    public int size() {
    	
        int i = 0;
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM course");
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
     * Get all courses in the database
     */
    @Override
    public Collection<Course> values() {
    	
        Collection<Course> col = new HashSet<Course>();
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM course");
            while (rs.next()) {
                col.add(new Course(rs.getString("id"),rs.getString("name"),rs.getString("teacher_id")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
}