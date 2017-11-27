package schedulemanager;

import schedulemanager.model.Model;
import schedulemanager.view.View;

/**
 * Main class that initializes the application
 */
public class ScheduleManager {

    public static void main(String[] args) {
        
        // Initialize model, view, and controller
        Model model = new Model();
        Controller controller = new Controller();
        View view = new View();
        
        // Pass model and view to controller
        controller.setModel(model);
        controller.setView(view);
        controller.attachToView();
        
        // Initialize model with courses
        model.createCourse("1", "Álgebra Linear EI");
        model.createCourse("2", "Cálculo");
        model.createCourse("3", "Tópicos de Matemática Discreta");
        model.createCourse("4", "Programação Funcional");
        model.createCourse("5", "Elementos de Engenharia de Sistemas");
        model.createCourse("6", "Laboratórios de Informática I");
        model.createCourse("7", "Sistemas de Computação");
        model.createCourse("8", "Análise");
        model.createCourse("9", "Tópicos de Física Moderna");
        model.createCourse("10", "Lógica EI");
        model.createCourse("11", "Programação Imperativa");
        model.createCourse("12", "Laboratórios de Informática II");
        model.createCourse("13", "Introdução aos Sistemas Dinâmicos");
        model.createCourse("14", "Estatística Aplicada");
        model.createCourse("15", "Engenharia Económica");
        model.createCourse("16", "Arquitetura de Computadores");
        model.createCourse("17", "Comunicação de Dados");
        model.createCourse("18", "Algoritmos e Complexidade");
        model.createCourse("19", "Sistemas Operativos");
        model.createCourse("20", "Programação Orientada aos Objetos");
        model.createCourse("21", "Eletromagnetismo EE");
        model.createCourse("22", "Cálculo de Programas");
        model.createCourse("23", "Laboratórios de Informática III");
        model.createCourse("24", "Bases de Dados");
        model.createCourse("25", "Desenvolvimento de Sistemas de Software");
        model.createCourse("26", "Modelos Determinísticos de Investigação Operacional");
        model.createCourse("27", "Sistemas Distribuídos");
        model.createCourse("28", "Redes de Computadores");
        model.createCourse("29", "Métodos Numéricos e Otimização não Linear");
        model.createCourse("30", "Sistemas de Representação de Conhecimento e Raciocínio");
        model.createCourse("31", "Computação Gráfica");
        model.createCourse("32", "Modelos Estocásticos de Investigação Operacional");
        model.createCourse("33", "Comunicações por Computador");
        model.createCourse("34", "Processamento de Linguagens");
        model.createCourse("35", "Laboratórios de Informática IV");
        
        controller.ucsName();
        
        // Show view
        view.setVisible(true);
 
    }
    
}
