/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulemanager.model;

import java.io.*;
import java.util.*;

public class IO {
    
    private Model model;
    
    public IO(Model model) {
        this.model = model;
    }
   
    public void save() {

        ObjectOutputStream oos = null;
        FileOutputStream f = null;

        try {
            f = new FileOutputStream("estado");

            oos = new ObjectOutputStream(f);
            
            oos.writeObject(model.getCourses());
            oos.writeObject(model.getStudents());
            oos.writeObject(model.getTeachers());
            oos.writeObject(model.getSwaps());
            
            oos.close();
        }

        catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }

        System.out.println("Saved!!!");
    }


    public void load() {
        // creating input stream variables
        FileInputStream fileinput = null;
        ObjectInputStream ois = null;

        try {

            // reading data
            fileinput = new FileInputStream("estado");

            // converting data to object
            ois = new ObjectInputStream(fileinput);

            // reading object's value
            
            HashMap<String, Swap> newMapSwaps = new HashMap<String, Swap>();
            newMapSwaps = (HashMap<String, Swap>) ois.readObject();

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
        
        System.out.println("Loaded!!!");
    }
}
