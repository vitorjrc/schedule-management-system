package schedulemanager;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        view.saveButton(this::saveButton);
        view.saveButton(this::loadButton);
        view.checkedCourse(this::checkedCourse);
        view.checkedOfferedShift(this::checkedOfferedShift);
        view.swapOffer(this::swapOffer);
        
    }
    
    // Called when the view sends an onRegister event
    private void onRegister(ArrayList<String> data) {
        view.getRegistrationArea().showCourses(ucs);
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
                
                String courseID = ucs.get(data.get(i));
                Shift newShift0 = model.createShift("PL0", courseID, 30, "Elfrida", "A4");
                model.createShift("PL1", courseID, 30, "Caiado", "A5");
                
                newCourses.add(courseID);
                s.assignShift(newShift0);
                
                Set<String> shiftIDs = model.getCourses().get(courseID).getShifts().keySet();
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
                this.s = model.getLoggedinStudent(userID);
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
        Set<String> shift = new HashSet<>();

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
        
        this.showTeachers();
        this.showAllCourses();
        
    }
    
    // metodo que traz a disciplina que o user quer trocar
    private void checkedCourse(ArrayList<String> data) {
        
        String courseName = data.get(0);
        String courseID = ucs.get(courseName);
        
        Set<String> shift = shift = s.getShiftsByCourse().get(courseID).keySet();
        
        ArrayList<String> myShifts = new ArrayList<String>();  
        for (String str : shift)  
            myShifts.add(str);
        
        view.myShifts(myShifts);
        
    }
    
    private void checkedOfferedShift(ArrayList<String> data) {
        
        String courseName = data.get(0);
        String courseID = ucs.get(courseName);
        
        String offeredShift = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<String>();        
        
        Set<String> shiftIDs = model.getCourses().get(courseID).getShifts().keySet();
        
        for(String s: shiftIDs) {
            if (!offeredShift.equals(s))
                shiftsList.add(s);
        }
        
        view.setShiftsList(shiftsList);
    }
    
    private void swapOffer(ArrayList<String> data) {
        
        String bidderID = s.getID();
        String courseID = ucs.get(data.get(0));
        String wantedShiftID = data.get(2);
        String offeredShiftID = data.get(1);
        
        System.out.println(bidderID + courseID + wantedShiftID + offeredShiftID);
        
        model.createSwapOffer(bidderID, courseID, offeredShiftID, wantedShiftID);
        this.showPendingOffers();
    }
    
    private void showPendingOffers() {
        
        ArrayList<String> pendingSwaps = new ArrayList<String>();
        for (Swap s: model.getOpenSwaps().values()) {
            String UC = model.getCourses().get(s.getCourseID()).getName();
            pendingSwaps.add("UC: " + UC + " Turno Oferecido: " + s.getShiftOfferedID() + " Turno pretendido: " + s.getShiftWantedID() + 
                    " Aluno " + s.getBidderID() + " Data: " + LocalDateTime.ofInstant(s.getDateCreated(), ZoneId.systemDefault()));
        }
        
        view.showPendentOffers(pendingSwaps);
    }
    
    //////////////////////// CRIAR TURNOS / CRIAR UCS
    //////////////////////// ADMIN
    
    private void showTeachers() {
        
        model.registerTeacher("1", "JBB", "123", "18");
        
        ArrayList<String> teachers = new ArrayList<String>();
        for (Teacher t: model.getTeachers().values())
            teachers.add(t.getName());
        
        view.showTeachers(teachers);
    }
    
    private void showAllCourses() {
        
        ArrayList<String> coursesList = new ArrayList<String>();
        
        Set<String> courses = this.ucs.keySet();
        
        for(String s: courses) {
                coursesList.add(s);
        
        view.showCourses(coursesList);
        }
    }
}
