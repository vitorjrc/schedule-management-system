package schedulemanager;

import schedulemanager.model.Model;
import schedulemanager.view.View;

/**
 * Main class that initializes the application
 */
public class ScheduleManager {

    public static void main(String[] args) {
        
        // Initialize model, view, and controller
        Model model = new Model();
        Controller controller = new Controller();
        View view = new View();
        
        // Pass model and view to controller
        controller.setModel(model);
        controller.setView(view);
    }
    
}
