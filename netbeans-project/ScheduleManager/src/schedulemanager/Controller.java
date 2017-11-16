package schedulemanager;

import java.util.ArrayList;
import schedulemanager.model.Model;
import schedulemanager.view.View;

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
    
    public void attachToView() {
    	view.onRegister(this::onRegister);
        view.getRegistrationArea().RegisterButton(this::RegisterButton);
    }
    
    // Called when the view sends a onRegister event
    private void onRegister(ArrayList<String> data) {
        view.openRegistrationArea().showCourses(model.getCourses());
    }
    
    private void RegisterButton(ArrayList<String> data) {
        // view.getRegistrationArea().showCourses(model.getCourses());
        for(int i = 0; i < data.size(); i++) {   
            System.out.print(data.get(i));
    }  
}
    
}
