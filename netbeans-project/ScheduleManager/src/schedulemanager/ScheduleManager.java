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
        model.createCourse("1", "Álgebra Linear EI", "teste");
        model.createCourse("2", "Cálculo", "teste");
        model.createCourse("3", "Tópicos de Matemática Discreta", "teste");
        model.createCourse("4", "Programação Funcional", "teste");
        model.createCourse("5", "Elementos de Engenharia de Sistemas", "teste");
        model.createCourse("6", "Laboratórios de Informática I", "teste");
        model.createCourse("7", "Sistemas de Computação", "teste");
        model.createCourse("8", "Análise", "teste");
        model.createCourse("9", "Tópicos de Física Moderna", "teste");
        model.createCourse("10", "Lógica EI", "teste");
        model.createCourse("11", "Programação Imperativa", "teste");
        model.createCourse("12", "Laboratórios de Informática II", "teste");
        model.createCourse("13", "Introdução aos Sistemas Dinâmicos", "teste");
        model.createCourse("14", "Estatística Aplicada", "teste");
        model.createCourse("15", "Engenharia Económica", "teste");
        model.createCourse("16", "Arquitetura de Computadores", "teste");
        model.createCourse("17", "Comunicação de Dados", "teste");
        model.createCourse("18", "Algoritmos e Complexidade", "teste");
        model.createCourse("19", "Sistemas Operativos", "teste");
        model.createCourse("20", "Programação Orientada aos Objetos", "teste");
        model.createCourse("21", "Eletromagnetismo EE", "teste");
        model.createCourse("22", "Cálculo de Programas", "teste");
        model.createCourse("23", "Laboratórios de Informática III", "teste");
        model.createCourse("24", "Bases de Dados", "teste");
        model.createCourse("25", "Desenvolvimento de Sistemas de Software", "teste");
        model.createCourse("26", "Modelos Determinísticos de Investigação Operacional", "teste");
        model.createCourse("27", "Sistemas Distribuídos", "teste");
        model.createCourse("28", "Redes de Computadores", "teste");
        model.createCourse("29", "Métodos Numéricos e Otimização não Linear", "teste");
        model.createCourse("30", "Sistemas de Representação de Conhecimento e Raciocínio", "teste");
        model.createCourse("31", "Computação Gráfica", "teste");
        model.createCourse("32", "Modelos Estocásticos de Investigação Operacional", "teste");
        model.createCourse("33", "Comunicações por Computador", "teste");
        model.createCourse("34", "Processamento de Linguagens", "teste");
        model.createCourse("35", "Laboratórios de Informática IV", "teste");
        model.registerTeacher("1", "JBB", "123");
        
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
