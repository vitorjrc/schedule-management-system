package coursemanager;

import java.time.ZoneId;
import java.util.*;
import coursemanager.model.*;
import coursemanager.view.*;
import java.time.format.*;

/**
 * Controller class - The only one that knows how view and model are implemented and depends on that implementation.
 *                    It connects those two modules together to form a beautiful work of bug-free art.
 *                    The controller should be minimal and simply connect the view and the model.
 *                    For user events, it subscribes to the view, pushes those changes to the model, and finishes up by updating the view back.
 */
public class Controller {
    
    private Model model;
    private View view;
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    public void setView(View view) {
        this.view = view;
    }
    
    // Tell view what methods from this class to call when certain events happen
    public void attachToView() {
        
        view.getLoginArea().loginButton(this::loginButton);
        view.createUC(this::createUC);
        view.changeCourseName(this::changeCourseName);
        view.createTeacher(this::createTeacher);
        view.createStudent(this::createStudent);
        
    }
    
    private void loginButton(ArrayList<String> data) {
        
        String userID = data.get(0);
        String userPassword = data.get(1);
        
        if (model.login(userID, userPassword) == 0) {
            view.LoginSuccess();
            
            //System.out.println(model.showStudentUCs(Integer.parseInt("78985")));
            // System.out.println(model.showTeacherStudents(Integer.parseInt("79194")));
            view.showStudentUCs(model.showStudentUCs(Integer.parseInt("78985")));
            view.showTeacherStudents(model.showTeacherStudents(Integer.parseInt("79194")));
            view.showStudentsToAdmin(model.showAllStudents());
            view.showTopUCs(model.getTop());
            view.showStats(model.numberOfUCs(), model.numberOfStudents());
            
            
            this.adminInterface();
        }
        
        else if (model.login(userID, userPassword) == 1) {
            view.LoginSuccess();
            view.showStudentUCs(model.showStudentUCs(Integer.parseInt("78985")));
            view.studentInterface();
        }
        
        else if (model.login(userID, userPassword) == 2) {
            view.LoginSuccess();
            //view.teacherInterface();
        }
        
    }
    
    private void adminInterface() {
        
        view.adminInterface();
        view.showCourses(model.getUCs());
    }
    
    
    private void createUC(ArrayList<String> data) {
        
        int newID = Integer.parseInt(data.get(0));
        String newName = data.get(1);
        int newYear = Integer.parseInt(data.get(2));
        int newECTS = Integer.parseInt(data.get(3));
        
        if (!model.createUC(newID, newName, newYear, newECTS)) {
            view.showSucessMessage();
        }
        else view.showLoginError("Operação falhada!");
        
    }
    
    private void changeCourseName(ArrayList<String> data) {
        
        String course = data.get(0);
        String newName = data.get(1);
        
        if (!model.changeCourseName(course, newName)) {
            view.showSucessMessage();  
         }
        else view.showLoginError("Operação falhada!");
    }
    
    private void createTeacher(ArrayList<String> data) {
        
        int newID = Integer.parseInt(data.get(0));
        String newName = data.get(1);
        String newSchool = data.get(2);
        
        if (!model.createTeacher(newID, newName, newSchool)) {
            view.showSucessMessage();
        }
        else view.showLoginError("Operação falhada!");
    }
    
    private void createStudent(ArrayList<String> data) {
        
        int newID = Integer.parseInt(data.get(0));
        String newName = data.get(1);
        String newSchool = data.get(2);
        
        if (!model.createStudent(newID, newName, newSchool)) {
            view.showSucessMessage();
        }
        else view.showLoginError("Operação falhada!");
    }
    
    
}
