package schedulemanager.model;

/**
 * Class that represents a student
 */

import java.util.*;
import java.io.Serializable;

public class Student implements Serializable {
    
    //VARIAVEIS DE INSTANCIA
    private static final long serialVersionUID = 7526472295622776147L;
    private String name;             
    private String id;                  
    private String password;                  
    private String status;                       
    
    //Lista com as ucs onde o student está inscrito
    private ArrayList<String> courses;      
   
    //CONSTRUTORES
    public Student(){
        this.name = "";
        this.id = "";
        this.password = "";
        this.status = "";
        this.courses = new ArrayList<>();
    }
    
    public Student(String name, String id, String pass, String stat, ArrayList<String> cours){
        this.name = name;
        this.id = id;
        this.password = pass;
        this.status = stat;
        this.courses = new ArrayList<String>();
        for(String s: cours)
           courses.add(s);  // enquanto a classe de disciplinas n é criada, vou usando Strings e são imutáveis logo n é preciso fazer s.clone()
    }
    
    public Student(Student s){
        this.name = s.getName();
        this.id = s.getID();
        this.password = s.getPassword();
        this.status = s.getStatus();
        this.courses = new ArrayList<>();
        for(String st: s.getCourses()) {
            courses.add(st); // não é preciso usar clone para string
        }
    }

    //METODOS DE INSTANCIA
    //GETTERS
    public String getName(){ return this.name; }
    
    public String getID(){ return this.id; }
    
    public String getPassword(){ return this.password; }
    
    public String getStatus(){ return this.status; }
    
    //devolve todas as disciplinas do student
    public ArrayList<String> getCourses(){
        ArrayList<String> ret = new ArrayList<String>();
        for(String s : this.courses) {
            ret.add(s); // não é preciso usar clone para string 
        }
        return ret;
    }
    
    //SETTERS
    public void setName(String name){ this.name = name; }
    
    public void setID(String id){ this.id = id; }
    
    public void setPassword(String pass){ this.password = pass; }
    
    public void setStatus(String stat){ this.status = stat; }
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o==null || o.getClass() != this.getClass()) return false;
        Student s = (Student) o;
        return ( this.name.equals(s.getName()) &&
                 this.id.equals(s.getID()) &&
                 this.password.equals(s.getPassword()) &&
                 this.status.equals(s.getStatus()) &&
                 this.courses.equals(s.getCourses())
               );
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("----------ALUNO----------\n");
        s.append("NOME: " +this.name+ "\n");
        s.append("ID: " +this.id+ "\n");
        s.append("PASSWORD: " +this.password+ "\n");
        s.append("REGIME: " +this.status+ "\n");
        s.append("UCS Inscrito: \n");
        for(String st : this.courses)
            s.append(st + "\n");
        s.append("-------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Student clone(){
        return new Student(this);
    }
}
