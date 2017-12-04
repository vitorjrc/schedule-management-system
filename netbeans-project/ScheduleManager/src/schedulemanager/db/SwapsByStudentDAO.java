package schedulemanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import schedulemanager.db.SwapDAO;
import schedulemanager.model.Swap;


public class SwapsByStudentDAO implements Map<String, SwapDAO> {
    
    private Connection conn;
    SwapDAO swapDAO = new SwapDAO();
    
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
            stm.executeUpdate("DELETE FROM HashMap<String, Swap> where id>0");
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
            String sql = "SELECT Id FROM Swap WHERE Id = ?";
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
        throw new NullPointerException("not implemented!");
    }
    
    
    /*
    *NAO MEXI
    */
    @Override
    public Set<Map.Entry<String, SwapDAO>> entrySet() {
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
    public SwapDAO get(Object key) {
        SwapDAO st = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Swap WHERE Id = ?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                st = new SwapDAO();
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
    public SwapDAO put(String key, SwapDAO value) {
        SwapDAO stud = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Student\n" +
                "VALUES (?)");
            stm.setInt(1, value.hashCode());

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
    public void putAll(Map<? extends String,? extends SwapDAO> t) {
        /*
        for(SwapDAO s : t.values()) {
            put(s.getId(), s);
        }
        */
        throw new NullPointerException("not implemented!");
    }
    
    /**
     * Remover um aluno, dado o seu numero
     * @param key
     * @return 
     * 
     * NAO MEXI
     */
    @Override
    public SwapDAO remove(Object key) {
        SwapDAO al = this.get(key);
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from Swap where id = ?");
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
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM Swap");
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
    public Collection<SwapDAO> values() {
        Collection<SwapDAO> col = new HashSet<SwapDAO>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Swap");
            while (rs.next()) {
                col.add(swapDAO);
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