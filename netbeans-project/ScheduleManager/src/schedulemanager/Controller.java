package schedulemanager;

import schedulemanager.model.Model;
import schedulemanager.view.View;
import java.util.*;
import java.awt.event.ActionEvent;

/**
 * Controller class - The only one that knows how view and model are implemented and depends on that implementation.
 *                    It connects those two modules together to form a beautiful work of bug-free art.
 *                    The controller should be minimal and simply connect the view and the model.
 *                    For user events, it subscribes to the view, pushes those changes to the model, and finishes up by updating the view back.
 */
public class Controller {
    
    private Model model;
    private View view;
    
    void setModel(Model model) {
        this.model = model;
    }
    
    void setView(View view) {
        this.view = view;
    }
    
    void showuc() {
        view.abreregisto().mostraucs(model.getPovoar());
    }
   
}
