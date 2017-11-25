package schedulemanager.model;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */

import java.util.*;

public class Model {
    
    private LinkedHashMap<String, Course> coursesList = new LinkedHashMap<String, Course>(); // Course ID -> Course
    private HashMap<String, Student> studentsList = new HashMap<String, Student>(); // Student ID -> Student
    
    public HashMap<String, Student> getStudents() {
        return this.studentsList;
    }
    
    private LinkedHashMap<String, Course> testCourses(LinkedHashMap<String, Course> ucs) {
        
    	ucs.put("1", new Course("1", "Álgebra Linear EI"));
        ucs.put("2", new Course("2", "Cálculo"));
        ucs.put("3", new Course("3", "Tópicos de Matemática Discreta"));
        ucs.put("4", new Course("4", "Programação Funcional"));
        ucs.put("5", new Course("5", "Elementos de Engenharia de Sistemas"));
        ucs.put("6", new Course("6", "Laboratórios de Informática I"));
        ucs.put("7", new Course("7", "Sistemas de Computação"));
        ucs.put("8", new Course("8", "Análise"));
        ucs.put("9", new Course("9", "Tópicos de Física Moderna"));
        ucs.put("10", new Course("10", "Lógica EI"));
        ucs.put("11", new Course("11", "Programação Imperativa"));
        ucs.put("12", new Course("12", "Laboratórios de Informática II"));
        ucs.put("13", new Course("13", "Introdução aos Sistemas Dinâmicos"));
        ucs.put("14", new Course("14", "Estatística Aplicada"));
        ucs.put("15", new Course("15", "Engenharia Económica"));
        ucs.put("16", new Course("16", "Arquitetura de Computadores"));
        ucs.put("17", new Course("17", "Comunicação de Dados"));
        ucs.put("18", new Course("18", "Algoritmos e Complexidade"));
        ucs.put("19", new Course("19", "Sistemas Operativos"));
        ucs.put("20", new Course("20", "Programação Orientada aos Objetos"));
        ucs.put("21", new Course("21", "Eletromagnetismo EE"));
        ucs.put("22", new Course("22", "Cálculo de Programas"));
        ucs.put("23", new Course("23", "Laboratórios de Informática III"));
        ucs.put("24", new Course("24", "Bases de Dados"));
        ucs.put("25", new Course("25", "Desenvolvimento de Sistemas de Software"));
        ucs.put("26", new Course("26", "Modelos Determinísticos de Investigação Operacional"));
        ucs.put("27", new Course("27", "Sistemas Distribuídos"));
        ucs.put("28", new Course("28", "Redes de Computadores"));
        ucs.put("29", new Course("29", "Métodos Numéricos e Otimização não Linear"));
        ucs.put("30", new Course("30", "Sistemas de Representação de Conhecimento e Raciocínio"));
        ucs.put("31", new Course("31", "Computação Gráfica"));
        ucs.put("32", new Course("32", "Modelos Estocásticos de Investigação Operacional"));
        ucs.put("33", new Course("33", "Comunicações por Computador"));
        ucs.put("34", new Course("34", "Processamento de Linguagens"));
        ucs.put("35", new Course("35", "Laboratórios de Informática IV"));

        return ucs;
    }
    
    public LinkedHashMap<String, Course> getUCsList() {
        
        LinkedHashMap<String, Course> newCoursesList = new LinkedHashMap<String, Course>(testCourses(coursesList));
        return newCoursesList;
    }

    
    public LinkedHashMap<String, Course> getCourses() {
        
        return testCourses(coursesList);
    }
    
    public Student registerStudent(String name, String id, String pass, String stat){
        Student newStudent = new Student(name, id, pass, stat);
        studentsList.put(id, newStudent);
        
        return newStudent;
    }
    
    // metodo que devolve os turnos de uma cadeira
    public ArrayList<String> courseShifts(String course) {
    
        ArrayList<String> courseShifts_list = new ArrayList<String>();
                
        for (Map.Entry<String, Shift> entry : this.coursesList.get(course).getShifts().entrySet()) {
            courseShifts_list.add(entry.getKey());
             System.out.println(entry.getKey());
        }
        
        return courseShifts_list;
    }
    
    public void createShiftCalculo() {
        Shift turnonovo = new Shift("PL1", "Cálculo", 10, "caiado", "A5");
        coursesList.get("2").addShift("PL1", turnonovo);
        
    }
    
    public void getShiftOfUser(String alunoID, ArrayList<Shift> shifts) {
        studentsList.get(alunoID).setShifts(shifts);
    }
    
}
