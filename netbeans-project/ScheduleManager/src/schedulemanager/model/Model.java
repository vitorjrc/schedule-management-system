package schedulemanager.model;

/**
 * Model class - The only one that knows about the data source. It knows nothing about neither view nor controller.
 */

import java.util.*;

public class Model {
    
    private ArrayList<String> ucs = new ArrayList<String>();
    
    private ArrayList<String> povoar(ArrayList<String> ucs) {
        
        ucs.add("Álgebra Linear EI");
        ucs.add("Cálculo");
        ucs.add("Tópicos de Matemática Discreta");
        ucs.add("Programação Funcional");
        ucs.add("Elementos de Engenharia de Sistemas");
        ucs.add("Laboratórios de Informática I");
        ucs.add("Sistemas de Computação");
        ucs.add("Análise");
        ucs.add("Tópicos de Física Moderna");
        ucs.add("Lógica EI");
        ucs.add("Programação Imperativa");
        ucs.add("Laboratórios de Informática II");
        ucs.add("Introdução aos Sistemas Dinâmicos");
        ucs.add("Estatística Aplicada");
        ucs.add("Engenharia Económica");
        ucs.add("Arquitetura de Computadores");
        ucs.add("Comunicação de Dados");
        ucs.add("Algoritmos e Complexidade");
        ucs.add("Sistemas Operativos");
        ucs.add("Programação Orientada aos Objetos");
        ucs.add("Eletromagnetismo EE");
        ucs.add("Cálculo de Programas");
        ucs.add("Laboratórios de Informática III");
        ucs.add("Bases de Dados");
        ucs.add("Desenvolvimento de Sistemas de Software");
        ucs.add("Modelos Determinísticos de Investigação Operacional");
        ucs.add("Sistemas Distribuídos");
        ucs.add("Redes de Computadores");
        ucs.add("Métodos Numéricos e Otimização não Linear");
        ucs.add("Sistemas de Representação de Conhecimento e Raciocínio");
        ucs.add("Computação Gráfica");
        ucs.add("Modelos Estocásticos de Investigação Operacional");
        ucs.add("Comunicações por Computador");
        ucs.add("Processamento de Linguagens");
        ucs.add("Laboratórios de Informática IV");
        
        return ucs;
    }
    
    public ArrayList<String> getPovoar() {
        
        return povoar(ucs);
    }
    
}
