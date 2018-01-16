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
    private Student userStudent = null;
    private Teacher userTeacher = null;
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    public void setView(View view) {
        this.view = view;
    }
    
    // Tell view what methods from this class to call when certain events happen
    public void attachToView() {
        
    	view.onRegister(this::onRegister);
        view.getRegistrationArea().RegisterButton(this::RegisterButton);
        view.getLoginArea().loginButton(this::loginButton);
        
    }
    
    // Called when the view sends an onRegister event
    private void onRegister(ArrayList<String> data) {
        
        
        view.getRegistrationArea().showCourses(courses);
    }
    
    
}
