package schedulemanager;

import schedulemanager.model.Model;
import schedulemanager.view.View;
import schedulemanager.db.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Main class that initializes the application
 */
public class ScheduleManager {

    
    
    //public static final String SELECT_QUERY = "SELECT Aluno.Numero, Aluno.Nome FROM Aluno;";
    
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
        model.registerTeacher("90", "JBB", "123");
        
        model.createCourse("1", "Álgebra Linear EI", "90");
        model.createCourse("2", "Cálculo", "90");
        model.createCourse("3", "Tópicos de Matemática Discreta", "90");
        model.createCourse("4", "Programação Funcional", "90");
        model.createCourse("5", "Elementos de Engenharia de Sistemas", "90");
        model.createCourse("6", "Laboratórios de Informática I", "90");
        model.createCourse("7", "Sistemas de Computação", "90");
        model.createCourse("8", "Análise", "90");
        model.createCourse("9", "Tópicos de Física Moderna", "90");
        model.createCourse("10", "Lógica EI", "90");
        model.createCourse("11", "Programação Imperativa", "90");
        model.createCourse("12", "Laboratórios de Informática II", "90");
        model.createCourse("13", "Introdução aos Sistemas Dinâmicos", "90");
        model.createCourse("14", "Estatística Aplicada", "90");
        model.createCourse("15", "Engenharia Económica", "90");
        model.createCourse("16", "Arquitetura de Computadores", "90");
        model.createCourse("17", "Comunicação de Dados", "90");
        model.createCourse("18", "Algoritmos e Complexidade", "90");
        model.createCourse("19", "Sistemas Operativos", "90");
        model.createCourse("20", "Programação Orientada aos Objetos", "90");
        model.createCourse("21", "Eletromagnetismo EE", "90");
        model.createCourse("22", "Cálculo de Programas", "90");
        model.createCourse("23", "Laboratórios de Informática III", "90");
        model.createCourse("24", "Bases de Dados", "90");
        model.createCourse("25", "Desenvolvimento de Sistemas de Software", "90");
        model.createCourse("26", "Modelos Determinísticos de Investigação Operacional", "90");
        model.createCourse("27", "Sistemas Distribuídos", "90");
        model.createCourse("28", "Redes de Computadores", "90");
        model.createCourse("29", "Métodos Numéricos e Otimização não Linear", "90");
        model.createCourse("30", "Sistemas de Representação de Conhecimento e Raciocínio", "90");
        model.createCourse("31", "Computação Gráfica", "90");
        model.createCourse("32", "Modelos Estocásticos de Investigação Operacional", "90");
        model.createCourse("33", "Comunicações por Computador", "90");
        model.createCourse("34", "Processamento de Linguagens", "90");
        model.createCourse("35", "Laboratórios de Informática IV", "90");
        
        
        controller.ucsName();
        
        // Show view
        view.setVisible(true);
        
/*
        String host = "jdbc:mysql://localhost:3306/trocaturnos";
        String uName = "admin";
        String uPass = "admin1";
        
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_QUERY); 
            
            while (rs.next()) {
                //read your lines one ofter one
                String id = rs.getString("Numero");
                String name = rs.getString("Nome");
                
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
 
    }
}
