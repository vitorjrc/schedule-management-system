package schedulemanager.model;

/**
 * Class that represents a student
 */

import java.util.*;
import java.io.Serializable;

public class Course implements Serializable {
    
    //VARIAVEIS DE INSTANCIA
    private static final long serialVersionUID = 7526472295622776147L;
    private String name;                                              
    
    // map com <PLX, Turno>
    private HashMap<String, Shift> shifts;      
   
    //CONSTRUTORES
    
    public Course(String name){
        this.name = name;
        this.shifts = new HashMap<String, Shift>();
        
    }
    
    public Course(Course s){
        this.name = s.getName();
        this.shifts = new HashMap<String, Shift>();
        for (Map.Entry<String, Shift> entry : getShifts().entrySet()){
            shifts.put(entry.getKey(),entry.getValue());
        }
    }
    
    public void createShift(String ID) {
        
        shifts.put(ID, new Shift(ID, 45, "Orlando", "A4"));
    }
    

    //METODOS DE INSTANCIA
    //GETTERS
    public String getName() { 
        return this.name; 
    }
    
    public HashMap<String, Shift> getShifts() { 
        return this.shifts; 
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(this.getName());
        return s.toString();
    }
    
    @Override
    public Course clone(){
        return new Course(this);
    }
}
