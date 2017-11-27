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
    
    private LinkedHashMap<String, String> ucs = new LinkedHashMap<String, String>(); // nameUC -> idUC
    private Student s = null;
    
    // Tell view what methods from this class to call when certain events happen
    public void attachToView() {
    	view.onRegister(this::onRegister);
        view.getRegistrationArea().RegisterButton(this::RegisterButton);
        view.getLoginArea().loginButton(this::loginButton);
        view.checkedCourse(this::checkedCourse);
        view.swapOffer(this::swapOffer);
        view.saveButton(this::saveButton);
        view.saveButton(this::loadButton);
    }
    
    // Called when the view sends an onRegister event
    private void onRegister(ArrayList<String> data) {
        view.getRegistrationArea().showCourses(ucsName());
    }
    
    public LinkedHashMap<String, String> ucsName() {
        
        for(Map.Entry<String, Course> entry: model.getCourses().entrySet()) {
            ucs.put(entry.getValue().getName(), entry.getKey());
        }
        
        return ucs;
    }
    
    
    private void RegisterButton(ArrayList<String> data) {
        
        String newID = data.get(0);
        String newPassword = data.get(1);
        String newName = data.get(2);
        String newStatus = data.get(3);
        
        String te = "Trabalhador-Estudante";
        String rn = "Regime Normal";
        
        if (newStatus.equals(te)) {
            newStatus = "workerstudent";
        }
        else if (newStatus.equals(rn)) {
            newStatus = "student";
        } else {
            throw new RuntimeException("Invalid student regimen in Controller");
        }
        
        if (newID.equals("") || newPassword.equals("") || newPassword.equals("")) {
            view.showRegisterError2();
        }
        
        else { 
            
            ArrayList<String> newCourses = new ArrayList<String>();
            s = model.registerStudent(newID, newName, newPassword, newStatus);
            
            for (int i = 4; i < data.size(); i++) {
                
                String st = ucs.get(data.get(i));
                Shift turnonovo = model.getCourses().get(st).createShift0();
                
                model.getCourses().get(st).addShift(st, turnonovo);
                newCourses.add(st);
                s.assignShift(turnonovo);
            }
            
            view.showRegisterSuccess();
        }
        
    }
    
    
    private void loginButton(ArrayList<String> data) {
        
        String userID = data.get(0);
        String userPassword = data.get(1);
        
        String message = model.login(userID, userPassword);
       
        if (message != null)
                view.showLoginError(message);     //msg de erro password incorreta
        else {
                view.showLoginSuccess();
                s = model.getLoggedinStudent(userID);
                // show interface things because user is logged
                showInterfaceThings(userID);
        }
        
    }
        
    private void saveButton(ArrayList<String> data) {
        
        model.save();
    }
    
    private void loadButton(ArrayList<String> data) {
        
        model.load();
    }
                
    
    public ArrayList<String> getShiftsofUser(String userID) {
            
        // UC e turno
        ArrayList<String> shiftsList = new ArrayList<String>();
        Set<String> shift = new LinkedHashSet<>();

        for (Map.Entry<String, HashMap<String, Shift>> entry: s.getShiftsByCourse().entrySet()) {
            
            String s1 = model.getCourses().get(entry.getKey()).getName();
            shift = entry.getValue().keySet();
            String joinShifts = String.join("-", shift);
            
            shiftsList.add(s1 + " Turno: " + joinShifts);
            
        }   
        
        return shiftsList;
    }
             
    
    private void showInterfaceThings(String userID) {
        
        ArrayList<String> courses = new ArrayList<String>();
        Set<String> coursesSet = new LinkedHashSet<>();
        
        // Get UCs name of user
        coursesSet = s.getShiftsByCourse().keySet();
        for(String s : coursesSet) {
            courses.add(model.getCourses().get(s).getName());
        }
        
        view.setCoursesList(courses);
        view.setLoggedAs(s.getName());
        view.setUserData(s.getID(), s.getRegimen());
       
        view.showUserUCs(getShiftsofUser(userID));
        
        view.showThingsAfterLogin();
        
    }
    
    // metodo que traz a disciplina que o user quer trocar
    private void checkedCourse(ArrayList<String> data) {
        String courseName = data.get(0);
        ArrayList<String> shiftsList = new ArrayList<String>();        
        
        String courseID = ucs.get(courseName);
        Set<String> shiftIDs = model.getCourses().get(courseID).getShifts().keySet();
        
        for(String s: shiftIDs) {
            
            shiftsList.add(s);
            
        }
        
        view.setShiftsList(shiftsList);
        
        // posteriormente, vamos usar a variavel loggedUserID para ir ver o turno do aluno e
        // tirar esse turno desta lista pq ele nao pode querer trocar para o turno onde ja esta
        
    }
    
    private void swapOffer(ArrayList<String> data) {
        
        String bidderID = s.getID();
        String courseID = ucs.get(data.get(0));
        String wantedShiftID = data.get(1);
        
        // pode melhorar
        String offeredShiftID = s.getShiftsByCourse().get(courseID).keySet().stream().findFirst().get();
        
        System.out.println(bidderID + " " + courseID + " " + wantedShiftID + " " + offeredShiftID);
        
        model.createSwapOffer(bidderID, courseID, offeredShiftID, wantedShiftID);
        this.showPendentOffers();
    }
    
    private void showPendentOffers() {
        
        ArrayList<String> pendentSwaps = new ArrayList<String>();
        for (Swap s: model.getOpenSwaps().values()) {
            pendentSwaps.add(s.toString());
        }
        
        view.showPendentOffers(pendentSwaps);
    }
}
