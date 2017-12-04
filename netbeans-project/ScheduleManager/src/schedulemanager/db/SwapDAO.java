package schedulemanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import schedulemanager.model.Swap;

public class SwapDAO implements Map<String, Swap> {
    
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
            stm.executeUpdate("DELETE FROM Swap where id>0");
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
            String sql = "SELECT `id` FROM Swap WHERE `id`=?;";
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
        Swap s = (Swap) value;
        return containsKey(s.getID());
    }
    
    
    /*
    *NAO MEXI
    */
    @Override
    public Set<Map.Entry<String,Swap>> entrySet() {
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
    public Swap get(Object key) {
        Swap st = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Swap WHERE id=?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                LocalDate dateCreated = LocalDate.parse(rs.getString("dateCreated"));
                Instant dateCreatedI = dateCreated.atStartOfDay(ZoneId.of("Europe/Lisbon")).toInstant();

                LocalDate dateTaken = LocalDate.parse(rs.getString("dateTaken"));
                Instant dateTakenI = dateTaken.atStartOfDay(ZoneId.of("Europe/Lisbon")).toInstant();
                
                st = new Swap(rs.getString("Id"), rs.getString("bidderID"), rs.getString("takerID"), rs.getString("shiftOfferedID"), rs.getString("shiftWantedID")
                        , dateCreatedI, dateTakenI, rs.getBoolean("isClosed"));
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
    public Swap put(String key, Swap value) {
        Swap stud = null;
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Aluno\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getBidderID());
            stm.setString(3, value.getTakerID());
            stm.setString(4, value.getCourseID());
            stm.setString(5, value.getShiftOfferedID());
            stm.setString(6, value.getShiftWantedID());
            stm.setString(7, value.getDateCreated().toString());
            stm.setString(8, value.getDateTaken().toString());
            stm.setBoolean(9, value.isClosed());

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
    public void putAll(Map<? extends String,? extends Swap> t) {
        for(Swap s : t.values()) {
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
    public Swap remove(Object key) {
        Swap al = this.get(key);
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from Swap where Id = ?");
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
    public Collection<Swap> values() {
        Collection<Swap> col = new HashSet<Swap>();
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Swap");
            while (rs.next()) {
                
                LocalDate dateCreated = LocalDate.parse(rs.getString("dateCreated"));
                Instant dateCreatedI = dateCreated.atStartOfDay(ZoneId.of("Europe/Lisbon")).toInstant();

                LocalDate dateTaken = LocalDate.parse(rs.getString("dateTaken"));
                Instant dateTakenI = dateTaken.atStartOfDay(ZoneId.of("Europe/Lisbon")).toInstant();
                
                col.add(new Swap(rs.getString("Id"), rs.getString("bidderID"), rs.getString("takerID"), rs.getString("shiftOfferedID"), rs.getString("shiftWantedID")
                        , dateCreatedI, dateTakenI, rs.getBoolean("isClosed")));
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