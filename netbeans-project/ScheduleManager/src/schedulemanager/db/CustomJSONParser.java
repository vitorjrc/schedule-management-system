package schedulemanager.db;

import schedulemanager.model.Student;
import schedulemanager.model.Course;

import java.io.*;
import java.util.Iterator;

import org.json.simple.*;
import org.json.simple.parser.*;

public class CustomJSONParser {
	
	private StudentDAO studentDAO;
	private CourseDAO courseDAO;
	
	public CustomJSONParser() {
		
		this.studentDAO = new StudentDAO();
		this.courseDAO = new CourseDAO();
	}

	public void loadStudentsToDB() {
		
		JSONParser parser = new JSONParser();
		
		try {

            Object obj = parser.parse(new FileReader("json/students.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            for (Iterator<?> iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                
            	String key = (String) iterator.next();
                
                JSONObject student = (JSONObject) jsonObject.get(key);
                
                Student s =  new Student(
                	(String) student.get("student_id"),
                	(String) student.get("name"),
                	(String) student.get("password"),
                	(String) student.get("regimen")
                );
                
                this.studentDAO.put(s.getID(), s);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}
	
	public void loadCoursesToDB() {
		
		JSONParser parser = new JSONParser();
		
		try {

            Object obj = parser.parse(new FileReader("json/courses.json"));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            for (Iterator<?> iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                
            	String key = (String) iterator.next();
                
                JSONObject course = (JSONObject) jsonObject.get(key);
                
                Course c =  new Course(
                	(String) course.get("shortname"),
                	(String) course.get("fullname")
                );
                
                this.courseDAO.put(c.getID(), c);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}
}
