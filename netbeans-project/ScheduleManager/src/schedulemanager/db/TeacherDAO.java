package schedulemanager.db;

import schedulemanager.db.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.model.Teacher;

public class TeacherDAO implements Map<String, Teacher> {
    
    private Connection conn;
    
    /**
     * Delete all teachers
     */
    @Override
    public void clear() {

        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM teacher");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Checks if a teacher with a given ID exists in the database
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT id FROM teacher WHERE id = ?";
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
     * Checks if a teacher exists in the database 
     * 
     * Provisional implementation - should check entire object and not just key
     */
    @Override
    public boolean containsValue(Object value) {
        Teacher s = (Teacher) value;
        return containsKey(s.getID());
    }
    
    /**
     * Return the set of entries
     */
    @Override
    public Set<Map.Entry<String,Teacher>> entrySet() {
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
     * Return a teacher with a given ID
     */
    @Override
    public Teacher get(Object key) {
        Teacher t = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM teacher WHERE id = ?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                t = new Teacher(rs.getString("id") ,rs.getString("name"), rs.getString("password"));
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
     * Insert a teacher into the database
     */
    @Override
    public Teacher put(String key, Teacher value) {
    	
    	// From Java docs:
        // Returns:
        // the previous value associated with key, or null if there was no mapping for key.
        // (A null return can also indicate that the map previously associated null with key,
        // if the implementation supports null values.)
    	
    	Teacher previous = this.get(key);
    	
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO teacher\n" +
                "VALUES (?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getName());
            stm.setString(3, value.getPassword());

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }

    /**
     * Insert a set of teachers into the database
     */
    @Override
    public void putAll(Map<? extends String, ? extends Teacher> t) {
        for(Teacher s : t.values()) {
            this.put(s.getID(), s);
        }
    }
    
    /**
     * Remove a teacher given their ID
     */
    @Override
    public Teacher remove(Object key) {
    	
        Teacher previous = this.get(key);
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from teacher where id = ?");
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
     * Return the number of teacher rows in the database
     */
    @Override
    public int size() {
    	
        int i = 0;
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM teacher");
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
     * Get all teachers in the database
     */
    @Override
    public Collection<Teacher> values() {
    	
        Collection<Teacher> col = new HashSet<Teacher>();
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM teacher");
            while (rs.next()) {
                col.add(new Teacher(rs.getString("id"),rs.getString("name"),rs.getString("password")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
}