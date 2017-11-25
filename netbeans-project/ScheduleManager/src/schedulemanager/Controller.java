package schedulemanager;

import java.util.*;
import schedulemanager.model.*;
import schedulemanager.view.*;

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
    
    // ID of currently logged in user
    private String loggedUserID;
    
    private LinkedHashMap<String, String> ucs = new LinkedHashMap<String, String>();
    
    
    // Tell view what methods from this class to call when certain events happen
    public void attachToView() {
    	view.onRegister(this::onRegister);
        view.getRegistrationArea().RegisterButton(this::RegisterButton);
        view.getLoginArea().loginButton(this::loginButton);
        view.checkedCourse(this::checkedCourse);
    }
    
    // Called when the view sends an onRegister event
    private void onRegister(ArrayList<String> data) {
        view.getRegistrationArea().showCourses(ucsName());
    }
    
    public LinkedHashMap<String, String> ucsName() {
        for(Map.Entry<String, Course> entry: model.getCourses().entrySet()) {
            ucs.put(entry.getKey(), entry.getValue().getName());
        }
        
        return ucs;
    }
    
    
    private void RegisterButton(ArrayList<String> data) {
        String new_ID = data.get(0);
        
        HashMap<String, Student> students = model.getStudents();
        if(students.containsKey(new_ID)) {
            view.showRegisterError1();   
            return;
        }
        
        String new_Password = data.get(1);
        String new_Name = data.get(2);
        String new_Status = data.get(3);
        
        if (new_ID.equals("") || new_Password.equals("") || new_Password.equals("")) {
            view.showRegisterError2();
        }
        
        else { 
            Student student = model.registerStudent(new_Name, new_ID, new_Password, new_Status);
            view.showRegisterSuccess();
            
                
        ArrayList<Shift> new_Courses = new ArrayList<Shift>();
        
        for (int i = 4; i < data.size(); i++) {
            Shift s = model.getUCsList().get(data.get(i)).Shift0();
            // testing Shift 1 for Calculo
            model.createShiftCalculo();
            new_Courses.add(s);
        }
        
        model.getShiftOfUser(new_ID, new_Courses);
        }
        
    }
    
    
    private void loginButton(ArrayList<String> data) {
        String userId = data.get(0);
        String userPassword = data.get(1);
        HashMap<String, Student> students = model.getStudents();
 
        if(!students.containsKey(userId))
            view.showLoginError1();         //msg de erro user inexistente
        else if(!students.get(userId).getPassword().equals(userPassword))
                view.showLoginError2();     //msg de erro password incorreta
        else {
                view.showLoginSuccess();    //msg login efetuado com sucesso
                // show interface things because user is logged
                showInterfaceThings(userId);
                loggedUserID = userId;
        }
                
    }
    
    private void showInterfaceThings(String userID) {
        view.setCoursesList(model.getStudents().get(userID).getUCsString());
        view.setLoggedAs(model.getStudents().get(userID).getName());
        view.setUserData(model.getStudents().get(userID).getID(), model.getStudents().get(userID).getRegimen());
        view.showUserUCs(model.getStudents().get(userID).getShiftsString());
        view.showThingsAfterLogin();
    }
    
    // metodo que traz a disciplina que o user quer trocar
    private void checkedCourse(ArrayList<String> data) {
        String course = data.get(0);
        
        view.setShiftsList(model.courseShifts(course));
        
        // posteriormente, vamos usar a variavel loggedUserID para ir ver o turno do aluno e
        // tirar esse turno desta lista pq ele nao pode querer trocar para o turno onde ja esta
        
    }
}
