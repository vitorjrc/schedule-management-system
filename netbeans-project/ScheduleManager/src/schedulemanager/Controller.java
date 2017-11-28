package schedulemanager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import schedulemanager.model.*;
import schedulemanager.view.*;
import java.time.format.*;

/**
 * Controller class - The only one that knows how view and model are implemented and depends on that implementation.
 *                    It connects those two modules together to form a beautiful work of bug-free art.
 *                    The controller should be minimal and simply connect the view and the model.
 *                    For user events, it subscribes to the view, pushes those changes to the model, and finishes up by updating the view back.
 */
public class Controller {
    
    private Model model;
    private View view;
    private LinkedHashMap<String, String> ucs = new LinkedHashMap<String, String>(); // nameUC -> idUC
    private Student s = null;
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    public void setView(View view) {
        this.view = view;
    }
    

    
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
        
        view.showCourseStudents(this::showCourseStudents);
        view.StudentShifts(this::StudentShifts);
        view.possibleStudentShifts(this::possibleStudentShifts);
        view.enrollButton(this::enrollButton);
        
        view.createTeacher(this::createTeacher);
        
        view.shiftsOfStudent(this::shiftsOfStudent);
        view.getStudentToRemove(this::getStudentToRemove);
        view.removeButton(this::removeButton);
        
        view.createShift(this::createShift);
        
        view.createCourse(this::createCourse);
        view.Logout(this::Logout);
        
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
                
    
    public HashMap<String, ArrayList<String>> getShiftsofUser(String userID) {
            
        // UC e turno
        HashMap<String, ArrayList<String>> userInfo = new HashMap<String, ArrayList<String>>();

        for (Map.Entry<String, HashMap<String, Shift>> entry: s.getShiftsByCourse().entrySet()) {
            
            String teacher = null;
            String classroom = null;
            
            String course = model.getCourses().get(entry.getKey()).getName();
            Set shift = entry.getValue().keySet();
            String joinedShifts = String.join("-", shift);
            
            for (Shift s: entry.getValue().values()) {
                teacher = s.getTeacher();
                classroom = s.getClassroom();
            }
            
            ArrayList<String> info = new ArrayList<String>();
            info.add(joinedShifts);
            info.add(classroom);
            info.add(teacher);
            
            userInfo.put(course,info);
        }
        
        return userInfo;
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
        
        /*
        this.showPendingOffers();
        this.showActiveOffers();
        this.showStudentOffersHistory();
*/
        
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
        
        model.createSwapOffer(bidderID, courseID, offeredShiftID, wantedShiftID);
        
        this.showPendingOffers();
        this.showActiveOffers();
        this.showStudentOffersHistory();
    }
    
    private void showPendingOffers() {
        
        ArrayList<ArrayList<String>> pendingSwaps = new ArrayList<ArrayList<String>>();
        for (Swap swap: model.getOpenSwaps().values()) {
            String UC = model.getCourses().get(swap.getCourseID()).getName();
            ArrayList<String> swapList = new ArrayList<String>();
            swapList.add(0, UC);
            swapList.add(1, swap.getShiftOfferedID());
            swapList.add(2, swap.getShiftWantedID());
            swapList.add(3, swap.getBidderID());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm").withZone(ZoneId.systemDefault());
            String date = formatter.format(swap.getDateCreated());
            
            swapList.add(4, date);      
            
            String takeable = String.valueOf(model.isSwapTakeable(s.getID(), swap.getID()));
            System.out.println(takeable);
            swapList.add(5, takeable);
            
            pendingSwaps.add(swapList);
        }
        
        view.showPendingOffers(pendingSwaps);
    }
    
    private void showActiveOffers() {
        ArrayList<String> activeOffers = new ArrayList<String>();
        
        for (Swap s: model.getOpenSwapsOfStudent(s.getID()).values()) {
            String UC = model.getCourses().get(s.getCourseID()).getName();
            activeOffers.add("UC: " + UC + " Turno Oferecido: " + s.getShiftOfferedID() + " Turno pretendido: " + s.getShiftWantedID() + 
                    " Aluno " + s.getBidderID() + " Data: " + LocalDateTime.ofInstant(s.getDateCreated(), ZoneId.systemDefault()));
        }
        
        view.showActiveOffers(activeOffers);
        
    }
    
    private void showStudentOffersHistory() {
        ArrayList<String> studentOffersHistory = new ArrayList<String>();

        for (Swap s: model.getClosedSwapsOfStudent(s.getID()).values()) {
            String UC = model.getCourses().get(s.getCourseID()).getName();
            studentOffersHistory.add("UC: " + UC + " Turno Oferecido: " + s.getShiftOfferedID() + " Turno pretendido: " + s.getShiftWantedID() + 
                    " Aluno " + s.getBidderID() + " Data: " + LocalDateTime.ofInstant(s.getDateCreated(), ZoneId.systemDefault()));
        }

        view.showActiveOffers(studentOffersHistory);
        
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
        }
        view.showCourses(coursesList);
    }
    
    // retornar na view os turnos da uc
    private void showCourseStudents(ArrayList<String> data) {
        
        String courseID = ucs.get(data.get(0));
        
        ArrayList<String> studentsList = new ArrayList<String>();

        for (Shift s: model.getCourses().get(courseID).getShifts().values()) {
            for (Student st: s.getOccupants().values()) {
                studentsList.add(st.getID());
            }
                
        }
        
        view.showCourseStudents(studentsList);
    }
    
    private void StudentShifts(ArrayList<String> data) {
        
        String courseID = ucs.get(data.get(0));
        String studentID = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<String>();
        
        Set<String> shifts = model.getStudents().get(studentID).getShiftsByCourse().get(courseID).keySet();
        
        for(String s: shifts) {
            shiftsList.add(s);
        }
        
        view.originShift(shiftsList);
    }
    
    private void possibleStudentShifts(ArrayList<String> data) {
        
        String courseID = ucs.get(data.get(0));
        String originShift = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<String>();
        
        // Set<String> shifts = model.getStudents().get(studentID).getShiftsByCourse().get(courseID).keySet();
        Set<String> ucShifts = model.getCourses().get(courseID).getShifts().keySet();
        
        for(String s: ucShifts) {
            shiftsList.add(s);
        }
        
        for (String s: shiftsList){
            shiftsList.remove(originShift);
        }
        

        
        view.destinationShift(shiftsList);
    }
    
    private void enrollButton(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        String selectedStudent = data.get(1);
        String originShift = data.get(2);
        String destinationShift = data.get(3);
        
        model.directSwap(selectedStudent, selectedCourse, originShift, destinationShift);
    }
    
    private void createTeacher(ArrayList<String> data) {
        
        String teacherName = data.get(0);
        String teacherID = data.get(1);
        String teacherPassword = data.get(2);
        String teacherCourse = ucs.get("Programação Funcional");
        
        model.registerTeacher(teacherID, teacherName, teacherPassword, teacherCourse);
        this.showTeachers();
        
    }
    
    private void shiftsOfStudent(ArrayList<String> data) {
        String selectedCourse = ucs.get(data.get(0));
        
        ArrayList<String> shiftsList = new ArrayList<String>();
        Set<String> ucShifts = model.getCourses().get(selectedCourse).getShifts().keySet();
        
        for (String s: ucShifts){
            shiftsList.add(s);
        }
        
        view.showShiftsofCourse(shiftsList);
        
    }
    
    private void getStudentToRemove(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        String selectedShift = data.get(1);
        
        ArrayList<String> shiftStudentsList = new ArrayList<String>();
        Set<String> shiftStudents = model.getCourses().get(selectedCourse).getShifts().get(selectedShift).getOccupants().keySet();
        
        for (String s: shiftStudents){
            shiftStudentsList.add(s);
        }
        
        view.showStudentToRemove(shiftStudentsList);
    }
        
    private void removeButton(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        String selectedShift = data.get(1);
        String selectedStudent = data.get(2);
        
        Shift s = model.getCourses().get(selectedCourse).getShift(selectedShift);
        model.getStudents().get(selectedStudent).removeFromShift(s);
        
    }
    
    private void createShift(ArrayList<String> data) {
            
        String selectedCourse = ucs.get(data.get(0));
        String newID = data.get(1);
        String newLimit = data.get(2);
        String newTeacher = data.get(3);
        String newClassroom = data.get(4);
        
        model.createShift(newID, selectedCourse, Integer.parseInt(newLimit), newTeacher, newClassroom);
        
        
    }
    
    private void createCourse(ArrayList<String> data) {
            
        String newID = data.get(0);
        String newName = data.get(1);
        String newTeacher = data.get(2);
        
        Course c = model.createCourse(newID, newName, newTeacher);

        this.ucs.put(c.getName(), c.getId());
        this.showAllCourses();
        
    }
    
    private void Logout(ArrayList<String> data) {
        
        model.logout();
    }
    
}
