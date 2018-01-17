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
        
        //view.getRegistrationArea().RegisterButton(this::RegisterButton);
        view.getLoginArea().loginButton(this::loginButton);
        
    }
    
    private void loginButton(ArrayList<String> data) {
        
        String userID = data.get(0);
        String userPassword = data.get(1);
        
        if (model.login(userID, userPassword) == 0) {
            view.LoginSuccess();
            
            System.out.println(model.showStudentUCs(Integer.parseInt("78985")));
            System.out.println(model.showTeacherStudents(Integer.parseInt("79194")));
            
            this.adminInterface();
        }
        
        else if (model.login(userID, userPassword) == 1) {
            view.LoginSuccess();
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
    
    // Called when the view sends an onRegister event
    private void onRegister(ArrayList<String> data) {
        
        
        //view.getRegistrationArea().showCourses(courses);
    }
    
    
}
