package schedulemanager;

import java.time.ZoneId;
import java.util.*;
import schedulemanager.model.*;
import schedulemanager.view.*;
import java.time.format.*;
import schedulemanager.db.*;

/**
 * Controller class - The only one that knows how view and model are implemented and depends on that implementation.
 *                    It connects those two modules together to form a beautiful work of bug-free art.
 *                    The controller should be minimal and simply connect the view and the model.
 *                    For user events, it subscribes to the view, pushes those changes to the model, and finishes up by updating the view back.
 */
public class Controller {
    
    private Model model;
    private View view;
    private Student userStudent = null;
    private Teacher userTeacher = null;
    
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
        view.loadButton(this::loadButton);
        
        view.checkedCourse(this::ShiftOfCourseSelected);
        view.checkedOfferedShift(this::destinationShiftPossible);
        view.swapOffer(this::swapOffer);
        
        view.showCourseStudents(this::showStudentsOfSelectedCourse);
        view.StudentShifts(this::StudentShifts);
        view.possibleStudentShifts(this::possibleStudentShifts);
        view.enrollButton(this::enrollButton);
        
        view.createTeacher(this::createTeacher);
        view.assignTeacher(this::assignTeacherToCourse);
        
        view.shiftsOfStudent(this::shiftsOfCourse);
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
        
        ArrayList<String> courses = new ArrayList<>();
        
        for (Course c: model.getCourses().values()) {
            courses.add(c.getName());
        }
        
        view.getRegistrationArea().showCourses(courses);
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
        
        if (newID.equals("") || newPassword.equals("") || found == 0) {
            view.showError1();
        }
        
        else { 
            
            userStudent = model.registerStudent(newID, newName, newPassword, newStatus);
            
            for (int i = 4; i < data.size(); i++) {
                
                String courseID = model.getIDOfCourse(data.get(i));
                model.assignStudentToCourse(userStudent.getID(), courseID);
            }
            
            
            view.showRegisterSuccess();
        }
        
    }
    
    
    private void loginButton(ArrayList<String> data) {
        
        String userID = data.get(0);
        String userPassword = data.get(1);
        
        String message = model.login(userID, userPassword);
       
        
        // login do authmanager returns null if login was successful, error message otherwise
        if (message != null)
                view.showLoginError(message);     //msg de erro password incorreta
        else {
                view.LoginSuccess();
                
                if (model.isAdminLoggedIn()) {
                    showAdminInterface();
                }
                
                if (model.isStudentLoggedIn()) {
                    
                    this.userStudent = (Student) model.getLoggedInUser();
                    this.userTeacher = null;
                    view.studentInterface();
                    showStudentInterface();
                }
                
                if (model.isTeacherLoggedIn()) {
                    
                    this.userStudent = null;
                    this.userTeacher = (Teacher) model.getLoggedInUser();
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
        
        String course = null;
        if (userTeacher.getCourseManagedID() == null) {
            view.showLoginError("Não tem unidade curricular atribuída.");
            return;
        }
        
        else course = model.getCourses().get(userTeacher.getCourseManagedID()).getName();
        
        view.teacherInterface(userTeacher.getName(), course);
    }
    
    private void loadButton(ArrayList<String> data) {

        model.loadCoursesToDB();
        model.loadStudentsToDB();
    }       
    
    private HashMap<String, ArrayList<String>> getShiftsOfUser() {
            
        // UC e turno
        HashMap<String, ArrayList<String>> userInfo = new HashMap<>();
        
        for (Course c: model.getCoursesOfStudent(userStudent.getID())) {
            
            ArrayList<String> info = new ArrayList<>();
            String shifts = "N/D";
            String teacher = "N/D";
            String classroom = "N/D";
            
            for (Shift s: model.getShiftsOfStudent(userStudent.getID())) {
                
                if (s.getCourseID().equals(c.getID())) {   
                
                    shifts = s.getID();
                    teacher = s.getTeacher();
                    classroom = s.getClassroom();
                }
            }
            
            info.add(shifts);
            info.add(classroom);
            info.add(teacher);
            
            userInfo.put(model.getNameOfCourse(c.getID()), info);
        }
        
        return userInfo;
    }
             
    
    private void showStudentInterface() {
        
        ArrayList<String> courses = new ArrayList<>();
        
        // Get UCs name of user
        ArrayList<Shift> coursesSet = new ArrayList<>(userStudent.getShifts());
        
        for (Shift s : coursesSet) {
            courses.add(model.getNameOfCourse(s.getCourseID()));
        }
        
        view.setCoursesList(courses);
        view.setUserData(userStudent.getName(), userStudent.getID(), userStudent.getRegimen());
      
        view.showUserUCs(getShiftsOfUser());
        
        
        this.update();
    }
    
    // metodo que devolve os turnos onde o aluno está inscrito na disciplina escolhida
    private void ShiftOfCourseSelected(ArrayList<String> data) {
        
        String courseName = data.get(0);
        String courseID = model.getIDOfCourse(courseName);
        
        ArrayList<String> myShifts = new ArrayList<>();  
        ArrayList<Shift> shifts = new ArrayList<>(userStudent.getShifts());
        
        for (Shift s: shifts) {
            if (s.getCourseID().equals(courseID)) {
                myShifts.add(s.getID());
            }
        } 
        
        view.myShifts(myShifts);
        
    }
    
    private void destinationShiftPossible(ArrayList<String> data) {
        
        String courseName = data.get(0);
        String courseID = model.getIDOfCourse(courseName);
        
        String offeredShift = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<>();

        for (Shift s: model.getShiftsOfCourse(courseID)) {
            shiftsList.add(s.getID());
        }
        
        shiftsList.remove(offeredShift);
        
        view.setShiftsList(shiftsList);
    }
    
    private void swapOffer(ArrayList<String> data) {
        
        String bidderID = userStudent.getID();
        String courseID = model.getIDOfCourse(data.get(0));
        String wantedShiftID = data.get(2);
        String offeredShiftID = data.get(1);
        
        if (courseID == null || wantedShiftID == null || offeredShiftID == null) {
            view.showError();
            return;
        }
        
        if (userStudent.getRegimen().equals("WORKERSTUDENT")) {
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
        
        if (model.getOpenSwaps() == null || model.getOpenSwaps().isEmpty()) return;
        
        for (Swap swap: model.getOpenSwaps().values()) {
            
            Shift s = model.getShifts().get(swap.getShiftOfferedID());
            String UC = model.getNameOfCourse(s.getCourseID());
            
            ArrayList<String> swapList = new ArrayList<>();
            
            swapList.add(0, UC);
            swapList.add(1, swap.getID());
            swapList.add(2, swap.getShiftOfferedID());
            swapList.add(3, swap.getShiftWantedID());
            swapList.add(4, swap.getBidderID());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm").withZone(ZoneId.systemDefault());
            String date = formatter.format(swap.getDateCreated());
            
            swapList.add(5, date);      
            
            String takeable = String.valueOf(model.isSwapTakeable(userStudent.getID(), swap.getID()));
            
            swapList.add(6, takeable);
            
            pendingSwaps.add(swapList);
        }
        
        view.showPendingOffers(pendingSwaps);
    }
    
    private void showActiveOffers() {
        
        if (model.getOpenSwapsOfStudent(userStudent.getID()) == null || model.getOpenSwapsOfStudent(userStudent.getID()).isEmpty()) return;
        
        ArrayList<ArrayList<String>> pendingSwapsofStudent = new ArrayList<>();
        for (Swap swap: model.getOpenSwapsOfStudent(userStudent.getID()).values()) {
            
            Shift s = model.getShifts().get(swap.getShiftOfferedID());
            String UC = model.getNameOfCourse(s.getCourseID());
            
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
        
        if (model.getClosedSwapsOfStudent(userStudent.getID()) == null || model.getClosedSwapsOfStudent(userStudent.getID()).isEmpty()) return;
        
        ArrayList<String> studentOffersHistory = new ArrayList<>();

        for (Swap swap: model.getClosedSwapsOfStudent(userStudent.getID()).values()) {
            
            Shift s = model.getShifts().get(swap.getShiftOfferedID());
            String UC = model.getNameOfCourse(s.getCourseID());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm").withZone(ZoneId.systemDefault());
            String date = formatter.format(swap.getDateTaken());

            studentOffersHistory.add(UC + " -> mudei do turno " + swap.getShiftOfferedID() + " para o " + swap.getShiftWantedID() + " em " + date);
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
        
        ArrayList<String> courses = new ArrayList<>();
        
        for (Course c: model.getCourses().values()) {
            courses.add(c.getName());
        }
        
        view.showCourses(courses);
    }
    
    // retornar na view os turnos da uc
    private void showStudentsOfSelectedCourse(ArrayList<String> data) {
        
        String courseID = model.getIDOfCourse(data.get(0));

        ArrayList<String> studentsList = new ArrayList<>(model.getStudentsInCourse(courseID)); 
        
        view.showCourseStudents(studentsList);
    }
    
    private void StudentShifts(ArrayList<String> data) {
        
        String courseID = model.getIDOfCourse(data.get(0));
        String studentID = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<>();

        for (Shift s: model.getStudents().get(studentID).getShifts()) {
            if (s.getCourseID().equals(courseID)) 
                shiftsList.add(s.getID());
        }
        
        if (shiftsList.isEmpty())
            shiftsList.add("N/D");
        
        view.originShift(shiftsList);
    }
    
    private void possibleStudentShifts(ArrayList<String> data) {
        
        String courseID = model.getIDOfCourse(data.get(0));
        String originShift = data.get(1);
        
        ArrayList<String> shiftsList = new ArrayList<>();

        for (Shift s: model.getShiftsOfCourse(courseID)) {
            shiftsList.add(s.getID());
        }
        
        shiftsList.remove(originShift);
        
        view.destinationShift(shiftsList);
    }
    
    private void enrollButton(ArrayList<String> data) {
        
        String selectedCourse = model.getIDOfCourse(data.get(0));
        String selectedStudent = data.get(1);
        String originShift = data.get(2);
        String destinationShift = data.get(3);
        
        if (selectedCourse == null || selectedStudent == null || originShift == null || destinationShift == null) {
            view.showError();
            return;
        }
        
        if (originShift.equals("N/D")) model.assignStudentToShift(selectedStudent, destinationShift);
        
        else model.directSwap(selectedStudent, selectedCourse, originShift, destinationShift);
        
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
    
    private void shiftsOfCourse(ArrayList<String> data) {
        
        String selectedCourse = model.getIDOfCourse(data.get(0));
        
        ArrayList<Shift> shiftsList = new ArrayList<>(model.getShiftsOfCourse(selectedCourse));
        ArrayList<String> ucShifts = new ArrayList<>();
        
        for (Shift s: shiftsList) {
            
            ucShifts.add(s.getID());
        }
        
        view.showShiftsofCourse(ucShifts);
        
    }
    
    private void getStudentToRemove(ArrayList<String> data) {
        
        // String selectedCourse = model.getIDOfCourse(data.get(0));
        String selectedShift = data.get(1);
        
        ArrayList<String> shiftStudentsList = new ArrayList<>(model.getStudentsInShift(selectedShift));
        
        view.showStudentToRemove(shiftStudentsList);
    }
        
    private void removeButton(ArrayList<String> data) {
        
        String selectedCourse = model.getIDOfCourse(data.get(0));
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
            
        String selectedCourse = model.getIDOfCourse(data.get(0));
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
        
        if (newID == null || newName == null) {
            view.showError();
            return;
        }
        
        if (model.getCourses().containsKey(newID)) {
            view.showError1();
            return;
        }
        
        model.createCourse(newID, newName);

        this.showAllCourses();
        
        view.showSucessMessage();
        
    }
    
    private void Logout(ArrayList<String> data) {
        
        this.userStudent = null;
        this.userTeacher = null;
        
        model.logout();
    }
        
    private void cancelOffer(ArrayList<String> data) {
        
        String swapID = data.get(0);
        
        model.cancelSwapOffer(userStudent.getID(), swapID);
        
        this.showStudentInterface();
    }
    
    private void acceptOffer(ArrayList<String> data) {
        
        String swapID = data.get(0);
        
        model.takeSwapOffer(userStudent.getID(), swapID);
        
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
    
    private void assignTeacherToCourse(ArrayList<String> data) {
        
        String courseID = data.get(0);
        String teacherID = data.get(1);
        
        model.assignTeacherToCourse(teacherID, courseID);
        view.showSucessMessage();
    }
}
