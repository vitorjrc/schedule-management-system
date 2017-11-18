package schedulemanager.model;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */

import java.util.*;

public class Model {
    
    private LinkedHashMap<String, Course> coursesList = new LinkedHashMap<String, Course>();
    private HashMap<String, Student> studentsList = new HashMap<String, Student>(); //String -> ID do aluno
    
    public HashMap<String, Student> getStudents() {
        return this.studentsList;
    }
    
    private LinkedHashMap<String, Course> testCourses(LinkedHashMap<String, Course> ucs) {
        
        ucs.put("Álgebra Linear EI", new Course("Álgebra Linear EI"));
        ucs.put("Cálculo", new Course("Cálculo"));
        ucs.put("Tópicos de Matemática Discreta", new Course("Tópicos de Matemática Discreta"));
        ucs.put("Programação Funcional", new Course("Programação Funcional"));
        ucs.put("Elementos de Engenharia de Sistemas", new Course("Elementos de Engenharia de Sistemas"));
        ucs.put("Laboratórios de Informática I", new Course("Laboratórios de Informática I"));
        ucs.put("Sistemas de Computação", new Course("Sistemas de Computação"));
        ucs.put("Análise", new Course("Análise"));
        ucs.put("Tópicos de Física Moderna", new Course("Tópicos de Física Moderna"));
        ucs.put("Lógica EI", new Course("Lógica EI"));
        ucs.put("Programação Imperativa", new Course("Programação Imperativa"));
        ucs.put("Laboratórios de Informática II", new Course("Laboratórios de Informática II"));
        ucs.put("Introdução aos Sistemas Dinâmicos", new Course("Introdução aos Sistemas Dinâmicos"));
        ucs.put("Estatística Aplicada", new Course("Estatística Aplicada"));
        ucs.put("Engenharia Económica", new Course("Engenharia Económica"));
        ucs.put("Arquitetura de Computadores", new Course("Arquitetura de Computadores"));
        ucs.put("Comunicação de Dados", new Course("Comunicação de Dados"));
        ucs.put("Algoritmos e Complexidade", new Course("Algoritmos e Complexidade"));
        ucs.put("Sistemas Operativos", new Course("Sistemas Operativos"));
        ucs.put("Programação Orientada aos Objetos", new Course("Programação Orientada aos Objetos"));
        ucs.put("Eletromagnetismo EE", new Course("Eletromagnetismo EE"));
        ucs.put("Cálculo de Programas", new Course("Cálculo de Programas"));
        ucs.put("Laboratórios de Informática III", new Course("Laboratórios de Informática III"));
        ucs.put("Bases de Dados", new Course("Bases de Dados"));
        ucs.put("Desenvolvimento de Sistemas de Software", new Course("Desenvolvimento de Sistemas de Software"));
        ucs.put("Modelos Determinísticos de Investigação Operacional", new Course("Modelos Determinísticos de Investigação Operacional"));
        ucs.put("Sistemas Distribuídos", new Course("Sistemas Distribuídos"));
        ucs.put("Redes de Computadores", new Course("Redes de Computadores"));
        ucs.put("Métodos Numéricos e Otimização não Linear", new Course("Métodos Numéricos e Otimização não Linear"));
        ucs.put("Sistemas de Representação de Conhecimento e Raciocínio", new Course("Sistemas de Representação de Conhecimento e Raciocínio"));
        ucs.put("Computação Gráfica", new Course("Computação Gráfica"));
        ucs.put("Modelos Estocásticos de Investigação Operacional", new Course("Modelos Estocásticos de Investigação Operacional"));
        ucs.put("Comunicações por Computador", new Course("Comunicações por Computador"));
        ucs.put("Processamento de Linguagens", new Course("Processamento de Linguagens"));
        ucs.put("Laboratórios de Informática IV", new Course("Laboratórios de Informática IV"));
        
        return ucs;
    }
    
    private void testShifts() {
        
        coursesList.get("Cálculo").createShift("PL1");
        coursesList.get("Cálculo").createShift("PL2");
        coursesList.get("Cálculo").createShift("PL3");
        coursesList.get("Cálculo").createShift("PL4");
        coursesList.get("Cálculo").createShift("PL5");
        
    }
    
    public LinkedHashMap<String, Course> getUCsList() {
        
        LinkedHashMap<String, Course> newCoursesList = new LinkedHashMap<String, Course>(testCourses(coursesList));
        this.testShifts();
        
        return newCoursesList;
    }
    
    /*
    public ArrayList<String> hashtoList() {
        
        this.testCourses(coursesList);
        this.testShifts();
        
        ArrayList<String> testtoList = new ArrayList<String>();
        
        for (String courses: coursesList.keySet() ) { 
            testtoList.add(courses);
        }
        
        return testtoList;
        
    }

   */
    
    public LinkedHashMap<String, Course> getCourses() {
        
        return testCourses(coursesList);
    }
    
    public Student registerStudent(String name, String id, String pass, String stat, ArrayList<String> courses){
        Student newStudent = new Student(name, id, pass, stat, courses);
        studentsList.put(id, newStudent);
        
        return newStudent;
    }
    
    // metodo que devolve os turnos de uma cadeira
    public ArrayList<String> courseShifts(String course) {
    
        ArrayList<String> courseShifts_list = new ArrayList<String>();
                
        for (Map.Entry<String, Shift> entry : this.coursesList.get(course).getShifts().entrySet()) {
            courseShifts_list.add(entry.getKey());
        }
        
        return courseShifts_list;
    }
    
    
    
}
