package schedulemanager;

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
    private LinkedHashMap<String, String> ucs = new LinkedHashMap<>(); // nameUC -> idUC
    private Student userS = null;
    private Teacher userT = null;
    
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
        
        view.cancelOffer(this::cancelOffer);
        view.acceptOffer(this::acceptOffer);
        
        view.lockSwaps(this::lockSwaps);
        view.unlockSwaps(this::unlockSwaps);
        
    }
    
    // Called when the view sends an onRegister event
    private void onRegister(ArrayList<String> data) {
        view.getRegistrationArea().showCourses(ucs);
    }
    
    public LinkedHashMap<String, String> ucsName() {
        
        for (Map.Entry<String, Course> entry: model.getCourses().entrySet()) {
            ucs.put(entry.getValue().getName(), entry.getKey());
        }
        
        return ucs;
    }
    
    
    private void RegisterButton(ArrayList<String> data) {
        
        String newID = data.get(0);
        
        if(model.getStudents().containsKey(newID) || model.getTeachers().containsKey(newID)) {
            view.showError1();
            return;
        }
        
        String newPassword = data.get(1);
        String newName = data.get(2);
        String newStatus = data.get(3);
        
        if(newStatus == null) {
            view.showError1();
            return;
        }
        
        
        String te = "Trabalhador-Estudante";
        String rn = "Regime Normal";
        
        if (newStatus.equals(te)) {
            newStatus = "workerstudent";
        }
        
        else if (newStatus.equals(rn)) {
            newStatus = "student";
            
        } else {
            newStatus = "null";
        }
        
        // Testing if user checked at least 1 course
        ArrayList<String> new_Courses = new ArrayList<>();
        
        int found = 0;
        for (int i = 4; i < data.size(); i++) {   
            
            new_Courses.add(data.get(i));
            
            if (!(data.get(i).equals("")))
                found = 1;
        }
        
        if (newID.equals("") || newPassword.equals("") || newPassword.equals("") || found == 0) {
            view.showError1();
        }
        
        else { 
            
            ArrayList<String> newCourses = new ArrayList<>();
            userS = model.registerStudent(newID, newName, newPassword, newStatus);
            
            for (int i = 4; i < data.size(); i++) {
                
                String courseID = ucs.get(data.get(i));
                Shift newShift0 = model.createShift("PL0", courseID, 30, "Elfrida", "A4");
                model.createShift("PL1", courseID, 30, "Caiado", "A5");
                
                newCourses.add(courseID);
                userS.assignShift(newShift0);
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
                view.LoginSuccess();
                
                if (model.isAdminLoggedIn()) {
                    showAdminInterface();
                }
                
                if (model.isStudentLoggedIn()) {
                    
                    this.userS = (Student) model.getLoggedInUser();
                    this.userT = null;
                    view.studentInterface();
                    showStudentInterface();
                }
                
                if (model.isTeacherLoggedIn()) {
                    
                    this.userS = null;
                    this.userT = (Teacher) model.getLoggedInUser();
                    showTeacherInterface();
                }
        }
        
    }
    
    private void showAdminInterface() {
        
        view.adminInterface();
        this.showTeachers();
        this.showAllCourses();
        
    }
    
    private void showTeacherInterface() {
        
        String course = model.getCourses().get(userT.getCourseManagedID()).getName();
        
        view.teacherInterface(userT.getName(), course);
    }
        
    private void saveButton(ArrayList<String> data) {
        
        model.save();
    }
    
    private void loadButton(ArrayList<String> data) {
        
        model.load();
        for (Course c: model.getCourses().values()) {
            
            if (ucs.containsKey(c.getName())) {
                continue;
            } 
            
            else {
                ucs.put(c.getName(), c.getId());
            }
        }
    }
                
    
    public HashMap<String, ArrayList<String>> getShiftsofUser(String userID) {
            
        // UC e turno
        HashMap<String, ArrayList<String>> userInfo = new HashMap<>();

        for (Map.Entry<String, HashMap<String, Shift>> entry: userS.getShiftsByCourse().entrySet()) {
            
            String teacher = null;
            String classroom = null;
            
            String course = model.getCourses().get(entry.getKey()).getName();
            Set shift = entry.getValue().keySet();
            String joinedShifts = String.join("-", shift);
            
            for (Shift s: entry.getValue().values()) {
                teacher = s.getTeacher();
                classroom = s.getClassroom();
            }
            
            ArrayList<String> info = new ArrayList<>();
            
            info.add(joinedShifts);
            info.add(classroom);
            info.add(teacher);
            
            userInfo.put(course,info);
        }
        
        return userInfo;
    }
             
    
    private void showStudentInterface() {
        
        ArrayList<String> courses = new ArrayList<>();
        
        // Get UCs name of user
        Set<String> coursesSet = userS.getShiftsByCourse().keySet();
        
        for (String s : coursesSet) {
            courses.add(model.getCourses().get(s).getName());
        }
        
        view.setCoursesList(courses);
        view.setUserData(userS.getName(), userS.getID(), userS.getRegimen());
       
        view.showUserUCs(getShiftsofUser(userS.getID()));
        
        this.showPendingOffers();
        if (model.getSwapsByStudentID().containsKey(userS.getID())) {
            this.showActiveOffers();
            this.showStudentOffersHistory();
        } 
        
    }
    
    // metodo que traz a disciplina que o user quer trocar
    private void checkedCourse(ArrayList<String> data) {
        
        String courseName = data.get(0);
        String courseID = ucs.get(courseName);
        
        Set<String> shift = userS.getShiftsByCourse().get(courseID).keySet();
        
        ArrayList<String> myShifts = new ArrayList<>();  
        
        for (String str : shift)  
            myShifts.add(str);
        
        view.myShifts(myShifts);
        
    }
    
    private void checkedOfferedShift(ArrayList<String> data) {
        
        String courseName = data.get(0);
        String courseID = ucs.get(courseName);
        
        String offeredShift = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<>();        
        
        Set<String> shiftIDs = model.getCourses().get(courseID).getShifts().keySet();
        
        for (String s: shiftIDs) {
            if (!offeredShift.equals(s))
                shiftsList.add(s);
        }
        
        view.setShiftsList(shiftsList);
    }
    
    private void swapOffer(ArrayList<String> data) {
        
        String bidderID = userS.getID();
        String courseID = ucs.get(data.get(0));
        String wantedShiftID = data.get(2);
        String offeredShiftID = data.get(1);
        
        if (courseID == null || wantedShiftID == null || offeredShiftID == null) {
            view.showError();
            return;
        }
        
        if (userS.getRegimen().equals("WORKERSTUDENT")) {
            model.directSwap(bidderID, courseID, offeredShiftID, wantedShiftID);
            this.showStudentInterface();
            view.showSucessMessage();
        }
        
        else {
        
        model.createSwapOffer(bidderID, courseID, offeredShiftID, wantedShiftID);
        
        this.update();
        view.showSucessMessage();
        
        }
    }
    
    private void showPendingOffers() {
        
        ArrayList<ArrayList<String>> pendingSwaps = new ArrayList<>();
        
        for (Swap swap: model.getOpenSwaps().values()) {
            
            String UC = model.getCourses().get(swap.getCourseID()).getName();
            
            ArrayList<String> swapList = new ArrayList<>();
            
            swapList.add(0, UC);
            swapList.add(1, swap.getID());
            swapList.add(2, swap.getShiftOfferedID());
            swapList.add(3, swap.getShiftWantedID());
            swapList.add(4, swap.getBidderID());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm").withZone(ZoneId.systemDefault());
            String date = formatter.format(swap.getDateCreated());
            
            swapList.add(5, date);      
            
            String takeable = String.valueOf(model.isSwapTakeable(userS.getID(), swap.getID()));
            
            swapList.add(6, takeable);
            
            pendingSwaps.add(swapList);
        }
        
        view.showPendingOffers(pendingSwaps);
    }
    
    private void showActiveOffers() {
        
        ArrayList<ArrayList<String>> pendingSwapsofStudent = new ArrayList<>();
        for (Swap swap: model.getOpenSwapsOfStudent(userS.getID()).values()) {
            
            String UC = model.getCourses().get(swap.getCourseID()).getName();
            
            ArrayList<String> swapInfo = new ArrayList<>();
            swapInfo.add(0, UC);
            swapInfo.add(1, swap.getShiftOfferedID());
            swapInfo.add(2, swap.getShiftWantedID());
            swapInfo.add(3, swap.getID());
            
            pendingSwapsofStudent.add(swapInfo);
        }
        
        view.showActiveOffers(pendingSwapsofStudent);
        
    }
    
    private void showStudentOffersHistory() {
        ArrayList<String> studentOffersHistory = new ArrayList<>();

        for (Swap s: model.getClosedSwapsOfStudent(userS.getID()).values()) {
            String UC = model.getCourses().get(s.getCourseID()).getName();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm").withZone(ZoneId.systemDefault());
            String date = formatter.format(s.getDateTaken());

            studentOffersHistory.add(UC + " -> mudei do turno " + s.getShiftOfferedID() + " para o " + s.getShiftWantedID() + " em " + date);
        }

        view.showStudentOffersHistory(studentOffersHistory);
        
    }
    
    //////////////////////// CRIAR TURNOS / CRIAR UCS
    //////////////////////// ADMIN
    
    private void showTeachers() {

        ArrayList<String> teachers = new ArrayList<>();
        for (Teacher t: model.getTeachers().values())
            teachers.add(t.getID());
        
        view.showTeachers(teachers);
    }
    
    private void showAllCourses() {
        
        ArrayList<String> coursesList = new ArrayList<>();
        
        Set<String> courses = this.ucs.keySet();
        
        for (String s: courses) {
                coursesList.add(s);
        }
        view.showCourses(coursesList);
    }
    
    // retornar na view os turnos da uc
    private void showCourseStudents(ArrayList<String> data) {
        
        String courseID = ucs.get(data.get(0));
        
        ArrayList<String> studentsList = new ArrayList<>();

        for (Shift s: model.getCourses().get(courseID).getShifts().values()) {
            
            for (Student st: s.getOccupants().values()) {
                
                // adding students to arrayList...
                studentsList.add(st.getID());
            }
                
        }
        
        // removing duplicates students
        Set<String> noDuplications = new HashSet<>();
        noDuplications.addAll(studentsList);
        studentsList.clear();
        studentsList.addAll(noDuplications);
        
        view.showCourseStudents(studentsList);
    }
    
    private void StudentShifts(ArrayList<String> data) {
        
        String courseID = ucs.get(data.get(0));
        
        ArrayList<String> shiftsList = new ArrayList<>();
        
        Set<String> shifts = model.getCourses().get(courseID).getShifts().keySet();
        
        for (String s: shifts) {
            shiftsList.add(s);
        }
        
        view.originShift(shiftsList);
    }
    
    private void possibleStudentShifts(ArrayList<String> data) {
        
        String courseID = ucs.get(data.get(0));
        String originShift = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<>();
        
        Set<String> ucShifts = model.getCourses().get(courseID).getShifts().keySet();
        
        for (String s: ucShifts) {
            shiftsList.add(s);
        }
        
        for (String s: shiftsList) {
            shiftsList.remove(originShift);
        }
        
        view.destinationShift(shiftsList);
    }
    
    private void enrollButton(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        String selectedStudent = data.get(1);
        String originShift = data.get(2);
        String destinationShift = data.get(3);
        
        if (selectedCourse == null || selectedStudent == null || originShift == null || destinationShift == null) {
            view.showError();
            return;
        }
        
        model.directSwap(selectedStudent, selectedCourse, originShift, destinationShift);
        
        view.showSucessMessage();
    }
    
    private void createTeacher(ArrayList<String> data) {
        
        String teacherName = data.get(0);
        String teacherID = data.get(1);
        String teacherPassword = data.get(2);
        
        if (model.getTeachers().containsKey(teacherID)) {
            view.showError1();
            return;
        }
        
        if (teacherName == null || teacherID == null || teacherPassword == null) {
            view.showError();
            return;
        }
        
        model.registerTeacher(teacherID, teacherName, teacherPassword);
        
        view.showSucessMessage();
        
        this.showTeachers();
        
    }
    
    private void shiftsOfStudent(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        
        ArrayList<String> shiftsList = new ArrayList<>();
        
        Set<String> ucShifts = model.getCourses().get(selectedCourse).getShifts().keySet();
        
        for (String s: ucShifts) {
            
            shiftsList.add(s);
        }
        
        view.showShiftsofCourse(shiftsList);
        
    }
    
    private void getStudentToRemove(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        String selectedShift = data.get(1);
        
        ArrayList<String> shiftStudentsList = new ArrayList<>();
        
        Set<String> shiftStudents = model.getCourses().get(selectedCourse).getShifts().get(selectedShift).getOccupants().keySet();
        
        for (String s: shiftStudents) {
            
            shiftStudentsList.add(s);
        }
        
        view.showStudentToRemove(shiftStudentsList);
    }
        
    private void removeButton(ArrayList<String> data) {
        
        String selectedCourse = ucs.get(data.get(0));
        String selectedShift = data.get(1);
        String selectedStudent = data.get(2);
        
        if (selectedCourse == null || selectedShift == null || selectedStudent == null) {
            view.showError();
            return;
        }
        
        Shift shift = model.getCourses().get(selectedCourse).getShift(selectedShift);
        
        model.getStudents().get(selectedStudent).removeFromShift(shift);
        
        view.showSucessMessage();
        
    }
    
    private void createShift(ArrayList<String> data) {
            
        String selectedCourse = ucs.get(data.get(0));
        String newID = data.get(1);
        String newLimit = data.get(2);
        String newTeacher = data.get(3);
        String newClassroom = data.get(4);
        
        if (model.getCourses().get(selectedCourse).getShifts().containsKey(newID)) {
            view.showError1();
            return;
        }
        
        if (newID == null || newLimit == null || newTeacher == null|| newClassroom == null) {
            view.showError();
            return;
        }
        
        model.createShift(newID, selectedCourse, Integer.parseInt(newLimit), newTeacher, newClassroom);
        
        view.showSucessMessage();
        
    }
    
    private void createCourse(ArrayList<String> data) {
            
        String newID = data.get(0);
        String newName = data.get(1);
        String newTeacher = data.get(2);
        
        if (newID == null || newName == null || newTeacher == null) {
            view.showError();
            return;
        }
        
        if (model.getCourses().containsKey(newID)) {
            view.showError1();
            return;
        }
        
        Course c = model.createCourse(newID, newName, newTeacher);

        this.ucs.put(c.getName(), c.getId());
        this.showAllCourses();
        
        model.assignTeacherToCourse(newTeacher, newID);
        
        view.showSucessMessage();
        
    }
    
    private void Logout(ArrayList<String> data) {
        
        model.logout();
    }
        
    private void cancelOffer(ArrayList<String> data) {
        
        String swapID = data.get(0);
        
        model.cancelSwapOffer(userS.getID(), swapID);
        
        this.showStudentInterface();
        this.update();
    }
    
    private void acceptOffer(ArrayList<String> data) {
        
        String swapID = data.get(0);
        
        model.takeSwapOffer(userS.getID(), swapID);
        
        this.showStudentInterface();
    }
    
    private void update() {
        
        this.showPendingOffers();
        
        this.showActiveOffers();
        
        this.showStudentOffersHistory();
    }
    
    private void unlockSwaps(ArrayList<String> data) {
        
        model.unlockSwaps();
        view.showSucessMessage();
    }
    
    private void lockSwaps(ArrayList<String> data) {
        
        model.lockSwaps();
        view.showSucessMessage();
    }
    
}
