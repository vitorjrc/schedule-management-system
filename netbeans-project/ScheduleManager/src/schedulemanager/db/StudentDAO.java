package schedulemanager.db;

import schedulemanager.db.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.model.Student;

public class StudentDAO implements Map<String,Student> {
    
    private Connection conn;
    
    /**
     * Apagar todos os alunos
     * NAO MEXI
     */
    @Override
    public void clear () {
        throw new NullPointerException("clear not implemented!");
        /*
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM Student where id>0");
        } catch (Exception e) {
            //runtime exeption!
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
        */
    }
    
    /**
     * Verifica se um número de aluno existe na base de dados
     * @param key
     * @return
     * @throws NullPointerException 
     * 
     * NAO MEXI
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT Id FROM Student WHERE Id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            r = rs.next();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return r;
    }
    
    /**
     * Verifica se um aluno existe na base de dados
     * 
     * Esta implementação é provisória. Devia testar todo o objecto e não apenas a chave.
     * 
     * @param value
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public boolean containsValue(Object value) {
        Student s = (Student) value;
        return containsKey(s.getID());
    }
    
    
    /*
    *NAO MEXI
    */
    @Override
    public Set<Map.Entry<String,Student>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
    
    
    /*
    *NAO MEXI
    */    
    @Override
    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }
    
    /**
     * Obter um aluno, dado o seu número
     * @param key
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public Student get(Object key) {
        Student st = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Student WHERE id = ?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                st = new Student(rs.getString("Name") ,rs.getString("Id"), rs.getString("Password"), rs.getString("Regimen"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return st;
    }
    
    
    /*
    *NAO MEXI
    */
    @Override
    public int hashCode() {
        return this.conn.hashCode();
    }
    
    /**
     * Verifica se existem entradas
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    
    /*
    * NAO MEXI
    */
    
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }
    
    /**
     * Insere um aluno na base de dados
     * @param key
     * @param value
     * @return 
     * 
     * DONE
     */
    @Override
    public Student put(String key, Student value) {
        Student stud = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Student\n" +
                "VALUES (?, ?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getName());
            stm.setString(3, value.getPassword());
            stm.setString(4, value.getRegimen());

            stm.executeUpdate();
            
            stud = value;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return stud;
    }

    /**
     * Por um conjunto de alunos na base de dados
     * @param t 
     * 
     * NAO MEXI
     */
    @Override
    public void putAll(Map<? extends String,? extends Student> t) {
        for(Student s : t.values()) {
            put(s.getID(), s);
        }
    }
    
    /**
     * Remover um aluno, dado o seu numero
     * @param key
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public Student remove(Object key) {
        Student al = this.get(key);
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from Student where Id = ?");
            stm.setInt(1, (Integer)key);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        return al;
    }
    
    /**
     * Retorna o número de entradas na base de dados
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public int size() {
        int i = 0;
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Student");
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {throw new NullPointerException(e.getMessage());}
        finally {
            Connect.close(conn);
        }
        return i;
    }
    
    /**
     * Obtém todos os alunos da base de dados
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public Collection<Student> values() {
        Collection<Student> col = new HashSet<Student>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Student");
            while (rs.next()) {
                col.add(new Student(rs.getString("Name"),rs.getString("Id"),rs.getString("Password"),rs.getString("Regimen")));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Connect.close(conn);
        }
        return col;
    }
    
}