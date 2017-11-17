package schedulemanager.model;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */

import java.util.*;

public class Model {
    
    private ArrayList<Course> coursesList = new ArrayList<Course>();
    private HashMap<String, Student> studentsList = new HashMap<String, Student>(); //String -> ID do aluno
    
    public HashMap<String, Student> getStudents() {
        return this.studentsList;
    }
    
    private ArrayList<Course> testCourses(ArrayList<Course> ucs) {
        
        ucs.add(new Course("Álgebra Linear EI"));
        ucs.add(new Course("Cálculo"));
        ucs.add(new Course("Tópicos de Matemática Discreta"));
        ucs.add(new Course("Programação Funcional"));
        ucs.add(new Course("Elementos de Engenharia de Sistemas"));
        ucs.add(new Course("Laboratórios de Informática I"));
        ucs.add(new Course("Sistemas de Computação"));
        ucs.add(new Course("Análise"));
        ucs.add(new Course("Tópicos de Física Moderna"));
        ucs.add(new Course("Lógica EI"));
        ucs.add(new Course("Programação Imperativa"));
        ucs.add(new Course("Laboratórios de Informática II"));
        ucs.add(new Course("Introdução aos Sistemas Dinâmicos"));
        ucs.add(new Course("Estatística Aplicada"));
        ucs.add(new Course("Engenharia Económica"));
        ucs.add(new Course("Arquitetura de Computadores"));
        ucs.add(new Course("Comunicação de Dados"));
        ucs.add(new Course("Algoritmos e Complexidade"));
        ucs.add(new Course("Sistemas Operativos"));
        ucs.add(new Course("Programação Orientada aos Objetos"));
        ucs.add(new Course("Eletromagnetismo EE"));
        ucs.add(new Course("Cálculo de Programas"));
        ucs.add(new Course("Laboratórios de Informática III"));
        ucs.add(new Course("Bases de Dados"));
        ucs.add(new Course("Desenvolvimento de Sistemas de Software"));
        ucs.add(new Course("Modelos Determinísticos de Investigação Operacional"));
        ucs.add(new Course("Sistemas Distribuídos"));
        ucs.add(new Course("Redes de Computadores"));
        ucs.add(new Course("Métodos Numéricos e Otimização não Linear"));
        ucs.add(new Course("Sistemas de Representação de Conhecimento e Raciocínio"));
        ucs.add(new Course("Computação Gráfica"));
        ucs.add(new Course("Modelos Estocásticos de Investigação Operacional"));
        ucs.add(new Course("Comunicações por Computador"));
        ucs.add(new Course("Processamento de Linguagens"));
        ucs.add(new Course("Laboratórios de Informática IV"));
        
        return ucs;
    }
    
    public ArrayList<Course> getCourses() {
        
        return testCourses(coursesList);
    }
    
    public Student registerStudent(String name, String id, String pass, String stat, ArrayList<String> courses){
        Student newStudent = new Student(name, id, pass, stat, courses);
        studentsList.put(id, newStudent);
        
        return newStudent;
    }
    
    
    
}
