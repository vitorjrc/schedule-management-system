package schedulemanager.db;

import schedulemanager.model.Student;
import schedulemanager.model.Course;

import java.io.*;

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

            JSONArray jsonArray = (JSONArray) obj;
            
            for (int i = 0; i < jsonArray.size(); i++) {
                
                JSONObject student = (JSONObject) jsonArray.get(i);
                
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

            JSONArray jsonArray = (JSONArray) obj;
            
            for (int i = 0; i < jsonArray.size(); i++) {
                
                JSONObject course = (JSONObject) jsonArray.get(i);
                
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
