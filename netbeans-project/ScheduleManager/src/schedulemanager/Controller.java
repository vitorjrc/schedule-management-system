package schedulemanager;

import java.util.*;
import schedulemanager.model.Model;
import schedulemanager.model.Student;
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
    
    // variável que nos diz o ID do user
    private String loggedUserID;
    
    
    //dar conhecimento dos metodos ao controller
    public void attachToView() {
    	view.onRegister(this::onRegister);
        view.getRegistrationArea().RegisterButton(this::RegisterButton);
        view.getLoginArea().loginButton(this::loginButton);
        view.checkedCourse(this::checkedCourse);
    }
    
    // Called when the view sends a onRegister event
    private void onRegister(ArrayList<String> data) {
        view.openRegistrationArea().showCourses(model.getUCsList());
    }
    
    private void RegisterButton(ArrayList<String> data) {
        String new_ID = data.get(0);
        String new_Password = data.get(1);
        String new_Name = data.get(2);
        String new_Status = data.get(3);
                
        ArrayList<String> new_Courses = new ArrayList<String>();
        
        for(int i = 4; i < data.size(); i++) {   
            new_Courses.add(data.get(i));
        }
        
        model.registerStudent(new_Name, new_ID, new_Password, new_Status, new_Courses);
        
        for (String id: model.getStudents().keySet()) {

            String key = id.toString();
            String value = model.getStudents().get(id).toString();  
            System.out.println("id do aluno " + id + " " + value);  
        }    
    }
    
    private void loginButton(ArrayList<String> data) {
        String user_ID = data.get(0);
        String user_passwd = data.get(1);
        HashMap<String, Student> students = model.getStudents();
 
        if(!students.containsKey(user_ID))
            view.showLoginError1();         //msg de erro user inexistente
        else if(!students.get(user_ID).getPassword().equals(user_passwd))
                view.showLoginError2();     //msg de erro password incorreta
        else {
                view.showLoginSuccess();    //msg login efetuado com sucesso
                // show interface things because user is logged
                showInterfacethings(user_ID);
                loggedUserID = user_ID;
        }
                
    }
    
    private void showInterfacethings(String userID) {
        view.setCoursesList(model.getStudents().get(userID).getCourses());
        view.setLoggedAs(model.getStudents().get(userID).getName());
        view.setUserData(model.getStudents().get(userID).getName(), model.getStudents().get(userID).getStatus());
        view.showUserUCs(model.getStudents().get(userID).getCourses());
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
