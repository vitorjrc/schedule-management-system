package schedulemanager.db;

import schedulemanager.db.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.model.Teacher;

public class TeacherDAO implements Map<String,Teacher> {
    
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
            String sql = "SELECT `id` FROM RegTeacher WHERE `id`=?;";
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
        Teacher s = (Teacher) value;
        return containsKey(s.getID());
    }
    
    
    /*
    *NAO MEXI
    */
    @Override
    public Set<Map.Entry<String,Teacher>> entrySet() {
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
    public Teacher get(Object key) {
        Teacher st = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM RegTeacher WHERE id=?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                st = new Teacher(rs.getString("Name") ,rs.getString("Id"), rs.getString("Password"));
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
    public Teacher put(String key, Teacher value) {
        Teacher stud = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO RegTeacher\n" +
                "VALUES (?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getName());
            stm.setString(3, value.getPassword());

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
    public void putAll(Map<? extends String,? extends Teacher> t) {
        for(Teacher s : t.values()) {
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
    public Teacher remove(Object key) {
        Teacher al = this.get(key);
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from RegTeacher where id = ?");
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM RegTeacher");
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
    public Collection<Teacher> values() {
        Collection<Teacher> col = new HashSet<Teacher>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM RegTeacher");
            while (rs.next()) {
                col.add(new Teacher(rs.getString("Name"),rs.getString("Id"),rs.getString("Password")));
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