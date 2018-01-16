package coursemanager;

import coursemanager.model.Model;
import coursemanager.view.View;

/**
 * Main class that initializes the application
 */
public class CourseManager {
    
    public static void main(String[] args) {
        
        // Initialize model, view, and controller
        Model model = new Model();
        Controller controller = new Controller();
        View view = new View();
        
        // Pass model and view to controller
        controller.setModel(model);
        controller.setView(view);
        controller.attachToView();

        // Show view
        view.setVisible(true);
 
    }
}

