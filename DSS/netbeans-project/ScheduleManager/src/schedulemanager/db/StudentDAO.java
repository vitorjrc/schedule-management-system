package schedulemanager.db;

import schedulemanager.db.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.model.Student;

public class StudentDAO implements Map<String, Student> {
    
    private Connection conn;
    
    /**
     * Delete all students
     */
    @Override
    public void clear() {

        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM student");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Checks if a student with a given ID exists in the database
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT id FROM student WHERE id = ?";
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
     * Checks if a student exists in the database 
     * 
     * Provisional implementation - should check entire object and not just key
     */
    @Override
    public boolean containsValue(Object value) {
        Student s = (Student) value;
        return containsKey(s.getID());
    }
    
    /**
     * Return the set of entries
     */
    @Override
    public Set<Map.Entry<String,Student>> entrySet() {
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
     * Return a student with a given ID
     */
    @Override
    public Student get(Object key) {
        Student t = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM student WHERE id = ?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                t = new Student(rs.getString("id") ,rs.getString("name"), rs.getString("password"), rs.getString("regimen"));
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
     * Insert a student into the database
     */
    @Override
    public Student put(String key, Student value) {
    	
    	// From Java docs:
        // Returns:
        // the previous value associated with key, or null if there was no mapping for key.
        // (A null return can also indicate that the map previously associated null with key,
        // if the implementation supports null values.)
    	
    	Student previous = this.get(key);
    	
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO student\n" +
                "VALUES (?, ?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getName());
            stm.setString(3, value.getPassword());
            stm.setString(4, value.getRegimen());

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }

    /**
     * Insert a set of students into the database
     */
    @Override
    public void putAll(Map<? extends String, ? extends Student> t) {
        for(Student s : t.values()) {
            this.put(s.getID(), s);
        }
    }
    
    /**
     * Remove a student given their ID
     */
    @Override
    public Student remove(Object key) {
    	
        Student previous = this.get(key);
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from student where id = ?");
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
     * Return the number of student rows in the database
     */
    @Override
    public int size() {
    	
        int i = 0;
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM student");
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
     * Get all students in the database
     */
    @Override
    public Collection<Student> values() {
    	
        Collection<Student> col = new HashSet<Student>();
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                col.add(new Student(rs.getString("id"),rs.getString("name"),rs.getString("password"),rs.getString("regimen")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
}