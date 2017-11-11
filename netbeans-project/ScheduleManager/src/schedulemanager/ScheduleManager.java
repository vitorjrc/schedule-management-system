package schedulemanager;

/**
 * Main class that initializes the application
 */
public class ScheduleManager {

    /**
     * @param args the command line arguments
     */
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
