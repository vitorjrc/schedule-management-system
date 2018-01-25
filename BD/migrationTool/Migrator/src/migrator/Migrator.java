/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package migrator;

import java.io.FileWriter;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

/**
 *
 * @author Vitor Castro
 */
public class Migrator {

    public static void main(String[] args) throws Exception {

        SQLReader sql = new SQLReader(); // classe que tem tem os para ler

        ArrayList<Docente> docentes = new ArrayList<>();
        ArrayList<Aluno> alunos = new ArrayList<>();
        ArrayList<AlunosUC> ucs = new ArrayList<>();

        FileWriter docentesFile;
        FileWriter alunosFile;
        FileWriter ucsFile;

        ObjectMapper doc = new ObjectMapper();
        doc.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        doc.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        doc.writerWithDefaultPrettyPrinter();

        ObjectMapper alu = new ObjectMapper();
        alu.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        alu.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        alu.writerWithDefaultPrettyPrinter();

        ObjectMapper aluucs = new ObjectMapper();
        aluucs.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        aluucs.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        aluucs.writerWithDefaultPrettyPrinter();

        sql.iniciarCon(); // iniciar connection a base de dados

        sql.migrateDocentes(docentes);
        sql.migrateAlunos(alunos);
        sql.migrateUCS(ucs);

        docentesFile = new FileWriter("docentes.json");
        doc.writeValue(docentesFile, docentes);
        docentesFile.close();
        System.out.println("Sucessfully made JSON Object to file docentes.");

        alunosFile = new FileWriter("alunos.json");
        doc.writeValue(alunosFile, alunos);
        alunosFile.close();
        System.out.println("Sucessfully made JSON Object to file alunos.");

        ucsFile = new FileWriter("ucs.json");
        aluucs.writeValue(ucsFile, ucs);
        ucsFile.close();
        System.out.println("Sucessfully made JSON Object to file ucs.");

        sql.fecharCon();

        System.out.println("Docentes: " + doc);
        System.out.println("Alunos: " + alu);
        System.out.println("UCS: " + aluucs);

    }
}
