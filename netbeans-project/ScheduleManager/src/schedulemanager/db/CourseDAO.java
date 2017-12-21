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
     * Insert a course into the database
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
            
            if (value.getTeacherID() != null) {
            	stm.setString(3, value.getTeacherID());
            } else {
            	stm.setString(3, null);
            }

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
    
    public String getIDOfCourse(String name) {
        
        String ID = null;
        
        try {
            
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT course.id from course where name = ?");
            stm.setString(1, (String) name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                ID = rs.getString("id");
            }
            
            
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return ID;
    }
    
    public String getNameOfCourse(String id) {
        
        String name = null;
        
        try {
            
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT course.name from course where id = ?");
            stm.setString(1, (String) id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
            }
            
            
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return name;
    }
    
    public Collection<String> getStudentsInCourse(String courseID) {
    	
    	Collection<String> col = new HashSet<String>();
        
        try {
            conn = Connect.connect();

            PreparedStatement stm = conn.prepareStatement("SELECT * FROM student_course WHERE course_id = ?");
            stm.setString(1, courseID);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
            	
                col.add(rs.getString("student_id"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
    public Collection<Course> getCoursesOfStudent(String studentID) {
    	
    	Collection<Course> col = new HashSet<Course>();
        
        try {
            conn = Connect.connect();

            PreparedStatement stm = conn.prepareStatement("SELECT course_id FROM student_course WHERE student_id = ?");
            stm.setString(1, studentID);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
            	
            	Course c = this.get(rs.getString("course_id"));
            	
                col.add(c);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return col;
    }
    
    /**
     * Add a student to a course
     */
    public void assignStudentToCourse(String studentID, String courseID) {
    	
    	try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO student_course\n" +
                "VALUES (?, ?)");
            stm.setString(1, studentID);
            stm.setString(2, courseID);

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Remove a student from a course
     * 
     * Returns true if the removal was successful, false otherwise
     */
    public boolean removeStudentFromCourse(String studentID, String courseID) {
    	
    	if (!this.isStudentInCourse(studentID, courseID)) {
    		return false;
    	}
    	
    	try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM student_course WHERE student_id = ? AND course_id = ?");
            stm.setString(1, studentID);
            stm.setString(2, courseID);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
    	
    	return true;
    }
    
    /**
     * Check if a student is in a certain course
     */
    public boolean isStudentInCourse(String studentID, String courseID) {
    	
    	boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT student_id FROM student_course WHERE student_id = ? AND course_id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, studentID);
            stm.setString(2, courseID);
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
     * Assigns a teacher as the manager of a course
     */
    public void setCourseManager(String teacherID, String courseID) {
    	
    	try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("UPDATE course SET teacher_id=? WHERE id=?");
            stm.setString(1, teacherID);
            stm.setString(2, courseID);

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Get the manager of a given course
     */
    public String getManagerOfCourse(String courseID) {
        
        String name = null;
        
        try {
            
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT teacher_id from course where id = ?");
            stm.setString(1, courseID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("teacher_id");
            }
            
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return name;
    }
    
    /**
     * Get the course managed by a given teacher
     */
    public String getCourseManaged(String teacherID) {
        
        String name = null;
        
        try {
            
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT id from course where teacher_id = ?");
            stm.setString(1, teacherID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                name = rs.getString("id");
            }
            
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return name;
    }
    
}