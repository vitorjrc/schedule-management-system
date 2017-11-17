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
        String new_ID = data.get(0);
        String new_Password = data.get(1);
        String new_Name = data.get(2);
        String new_Status = data.get(3);
                
        ArrayList<String> new_Courses = new ArrayList<String>();
        
        for(int i = 4; i < data.size() - 1 ; i++) {   
            new_Courses.add(data.get(i));
        }
        
        model.registerStudent(new_Name, new_ID, new_Password, new_Status, new_Courses);
        
        for (String id: model.getStudents().keySet()){

            String key = id.toString();
            String value = model.getStudents().get(id).toString();  
            System.out.println("id do aluno " + id + " " + value);  


} 
    }
    
    
    
    
    
}
