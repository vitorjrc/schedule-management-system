package schedulemanager.db;

import schedulemanager.model.Student;
import schedulemanager.model.Course;
import schedulemanager.model.Shift;

import java.io.*;
import java.util.Collection;
import java.util.Random;

import org.json.simple.*;
import org.json.simple.parser.*;

public class CustomJSONParser {
	
	private StudentDAO studentDAO;
	private CourseDAO courseDAO;
	private ShiftDAO shiftDAO;
	
	public CustomJSONParser() {
		
		this.studentDAO = new StudentDAO();
		this.courseDAO = new CourseDAO();
		this.shiftDAO = new ShiftDAO();
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
                
                if (!this.studentDAO.containsKey(s.getID())) {                	
                	this.studentDAO.put(s.getID(), s);
                }
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
                
                if (!this.courseDAO.containsKey(c.getID())) {                	
                	this.courseDAO.put(c.getID(), c);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
	}
	
	public void clearDBStudents() {
		this.studentDAO.clear();
	}
	
	public void clearDBCourses() {
		this.courseDAO.clear();
	}
	
	public void enrollStudentsInCourses() {
		
		// Used for testing
		// Finds students in database with no courses,
		// and enrolls them in a given year's courses
		
		Collection<Student> students = this.studentDAO.values();
		
		for (Student s : students) {
			
			if (!this.courseDAO.isStudentInAnyCourse(s.getID())) {
				
				// Choose a random year between 1 and 3
				int year = new Random().nextInt(3) + 1;
				
				if (year == 1) {
					
					for (int i = 1; i <= 6; i++) {
						this.courseDAO.assignStudentToCourse(s.getID(), "H501N" + String.valueOf(i));
					}
					
					for (int j = 1; j <= 6; j++) {
						this.courseDAO.assignStudentToCourse(s.getID(), "H502N" + String.valueOf(j));
					}
					
				} else if (year == 2) {
					
					for (int i = 1; i <= 6; i++) {
						this.courseDAO.assignStudentToCourse(s.getID(), "H503N" + String.valueOf(i));
					}
					
					for (int j = 1; j <= 6; j++) {
						this.courseDAO.assignStudentToCourse(s.getID(), "H504N" + String.valueOf(j));
					}
					
				} else if (year == 3) {
					
					for (int i = 1; i <= 6; i++) {
						this.courseDAO.assignStudentToCourse(s.getID(), "H505N" + String.valueOf(i));
					}
					
					for (int j = 1; j <= 6; j++) {
						this.courseDAO.assignStudentToCourse(s.getID(), "H506N" + String.valueOf(j));
					}
					
				}
			}
		}
		
	}
	
	public void enrollStudentsInShifts() {
		
		// Shift allocation
		// Loop through every student,
		// if they are enrolled in a course but not in a shift of that course,
		// enroll them in the first that is not full
		
		Collection<Student> students = this.studentDAO.values();
		
		// Loop through every student
		for (Student s : students) {
			
			Collection<Course> courses = this.courseDAO.getCoursesOfStudent(s.getID());
			
			// Loop through every course of this student
			for (Course c : courses) {
				
				Collection<Shift> shifts = this.shiftDAO.getShiftsOfCourse(c.getID());
				
				boolean studentIsInAShift = false;
				
				// Check if student is in any shift of this course
				for (Shift shift : shifts) {
					
					if (this.shiftDAO.isStudentInShift(s.getID(), shift.getID())) {
						studentIsInAShift = true;
					}
				}
				
				// If student is not in any shift, assign them to first that isn't full
				if (!studentIsInAShift) {
					
					for (Shift shiftaroo : shifts) {
						
						if (shiftaroo.getOccupants().size() < shiftaroo.getOccupationLimit()) {
							
							this.shiftDAO.assignStudentToShift(s.getID(), shiftaroo.getID());
						}
					}
				}
			}
			
		}
	}
}
