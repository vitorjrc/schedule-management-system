

package schedulemanager.model;

/**
 * Class that represents a swap
*/

import java.util.*;
import java.io.Serializable;

public class Swap implements Serializable {
    
    //VARIAVEIS DE INSTANCIA
    private static final long serialVersionUID = 7526472295622776147L;
    private Student student1;             
    private Student student2;                  
    private Course course;
    private Shift shift;
    private Date date; 
   
    //CONSTRUTORES
    
    public Swap(Student student1, Course course, Shift shift, Date date){
        this.student1 = student1;
        this.student2 = null;
        this.course = course;
        this.shift = shift;
        this.date = date;
    }
    
    public Swap(Swap s){
        this.student1 = s.getStudent1();
        this.student2 = s.getStudent2();
        this.course = s.getCourse();
        this.shift = s.getShift();
        this.date = s.getDate();
    }

    //METODOS DE INSTANCIA
    //GETTERS
    public Student getStudent1(){ return this.student1.clone(); }
    
    public Student getStudent2(){ return this.student2.clone(); }
    
    public Course getCourse(){ return this.course.clone(); }
    
    public Shift getShift(){ return this.shift.clone(); }
    
    public Date getDate(){ return this.date; } //supostamente datas no Java 8 são imutaveis
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----------TROCA----------\n");
        s.append("Estudante 1: " +this.student1+ "\n");
        s.append("Estudante 2: " +this.student2+ "\n");
        s.append("UC: " +this.course+ "\n");
        s.append("Turno: " +this.shift+ "\n");
        s.append("Data: " + this.date + "\n");
        s.append("-------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Swap clone(){
        return new Swap(this);
    }
}
