/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migrator;

import java.io.FileWriter;
import java.util.ArrayList;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author Vitor Castro
 */
public class Migrator {

    public static void main(String[] args) throws Exception {

        SQLReader sql = new SQLReader(); // classe que tem tem os para ler

        ArrayList<Docente> docentes = new ArrayList<>();
        ArrayList<Aluno> alunos = new ArrayList<>();

        FileWriter docentesFile;
        FileWriter alunosFile;

        ObjectMapper doc = new ObjectMapper();
        doc.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        doc.writerWithDefaultPrettyPrinter();

        ObjectMapper alu = new ObjectMapper();
        alu.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        alu.writerWithDefaultPrettyPrinter();

        sql.iniciarCon(); // iniciar connection a base de dados

        sql.migrateDocentes(docentes);
        sql.migrateAlunos(alunos);

        docentesFile = new FileWriter("docentes.json");
        doc.writeValue(docentesFile, docentes);
        docentesFile.close();
        System.out.println("Sucessfully made JSON Object to file docentes.");

        alunosFile = new FileWriter("alunos.json");
        doc.writeValue(alunosFile, alunos);
        alunosFile.close();
        System.out.println("Sucessfully made JSON Object to file alunos.");

        sql.fecharCon();

        System.out.println("Docentes: " + doc);
        System.out.println("Alunos: " + alu);

    }
}
