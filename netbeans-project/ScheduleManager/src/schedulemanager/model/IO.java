/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulemanager.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class IO {
    
    private AuthManager authManager;
    private Model model;
    
    public IO(AuthManager authManager, Model model) {
        this.authManager = authManager;
        this.model = model;
    }
   
    public void save() {

    ObjectOutputStream oos = null;
    FileOutputStream f = null;

    try {
        f = new FileOutputStream("estado.txt");

        oos = new ObjectOutputStream(f);
        oos.writeObject(model.getCourses());
        oos.writeObject(authManager.getRegisteredStudents());
        oos.writeObject(authManager.getRegisteredTeachers());
        oos.close();
    }

    catch (FileNotFoundException fnfex) {
        fnfex.printStackTrace();
    }
    catch (IOException ioex) {
        ioex.printStackTrace();
    }

    System.out.println("Things saved");
}


    public void load() {
        // creating input stream variables
        FileInputStream fileinput = null;
        ObjectInputStream ois = null;

        try {

            // reading data
            fileinput = new FileInputStream("estado.txt");

            // converting data to object
            ois = new ObjectInputStream(fileinput);

            // reading object's value

            LinkedHashMap<String, Course> newMapCourse = new LinkedHashMap<String, Course>();
            newMapCourse = (LinkedHashMap<String, Course>) ois.readObject();
            model.replaceMapOfCourses(newMapCourse);

            HashMap<String, Student> newMapStudent = new HashMap<String, Student>();
            newMapStudent = (HashMap<String, Student>) ois.readObject();
            authManager.replaceMapOfStudents(newMapStudent);

            HashMap<String, Teacher> newMapTeacher = new HashMap<String, Teacher>();
            newMapTeacher = (HashMap<String, Teacher>) ois.readObject();
            authManager.replaceMapOfTeachers(newMapTeacher);


            ois.close();
        }
        catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }
        catch (ClassNotFoundException ccex) {
            ccex.printStackTrace();
        }
    }
}
