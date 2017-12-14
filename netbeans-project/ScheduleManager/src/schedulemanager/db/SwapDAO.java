package schedulemanager.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.*;
import schedulemanager.model.Swap;

public class SwapDAO implements Map<String, Swap> {
    
    private Connection conn;
    
    /**
     * Delete all swaps
     */
    @Override
    public void clear () {
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            stm.executeUpdate("DELETE FROM swap");
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
    }
    
    /**
     * Check if a swap ID exists in the database
     */
    @Override
    public boolean containsKey(Object key) throws NullPointerException {
        boolean r = false;
        try {
            conn = Connect.connect();
            String sql = "SELECT id FROM swap WHERE id = ?";
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
     * Check if a swap with a given ID exists in the database
     */
    @Override
    public boolean containsValue(Object value) {
        Swap s = (Swap) value;
        return containsKey(s.getID());
    }
    
    /**
     * entrySet
     */
    @Override
    public Set<Map.Entry<String,Swap>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
    
    
    /**
     * equals
     */    
    @Override
    public boolean equals(Object o) {
        throw new NullPointerException("public boolean equals(Object o) not implemented!");
    }
    
    /**
     * Get a swap given its ID
     */
    @Override
    public Swap get(Object key) {
    	
        Swap s = null;
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM swap WHERE id=?");
            stm.setString(1, key.toString());
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	
                Instant dateCreated = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_created")));
                Instant dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
                s = new Swap(
                		rs.getString("id"),
                		rs.getString("bidder_id"),
                		rs.getString("taker_id"),
                		rs.getString("shift_offered_id"),
                		rs.getString("shift_wanted_id"),
                		dateCreated,
                		dateTaken,
                		rs.getBoolean("is_closed")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        return s;
    }
    
    
    /**
     * hashCode
     */
    @Override
    public int hashCode() {
        return this.conn.hashCode();
    }
    
    /**
     * isEmpty
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    
    /**
     * keySet
     */
    @Override
    public Set<String> keySet() {
        throw new NullPointerException("Not implemented!");
    }
    
    /**
     * Insert a swap into the database
     */
    
    @Override
    public Swap put(String key, Swap value) {
    	
    	// From Java docs:
        // Returns:
        // the previous value associated with key, or null if there was no mapping for key.
        // (A null return can also indicate that the map previously associated null with key,
        // if the implementation supports null values.)
    	
    	Swap previous = this.get(key);
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO swap\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, value.getID());
            stm.setString(2, value.getBidderID());
            stm.setString(3, value.getTakerID());
            stm.setString(4, value.getShiftOfferedID());
            stm.setString(5, value.getShiftWantedID());
            stm.setString(6, String.valueOf(value.getDateCreated().toEpochMilli()));
            stm.setString(7, String.valueOf(value.getDateTaken().toEpochMilli()));
            stm.setBoolean(8, value.isClosed());

            stm.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
        
        return previous;
    }

    /**
     * Insert a set of swaps into the database
     */
    @Override
    public void putAll(Map<? extends String,? extends Swap> t) {
        for(Swap s : t.values()) {
            put(s.getID(), s);
        }
    }
    
    /**
     * Remove a swap from the database given its ID
     */
    @Override
    public Swap remove(Object key) {
    	
        Swap s = this.get(key);
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("delete from swap where Id = ?");
            stm.setInt(1, (Integer)key);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        } finally {
            Connect.close(conn);
        }
        
        return s;
    }
    
    /**
     * Return the number of swap entries in the database
     */
    @Override
    public int size() {
        int i = 0;
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT count(*) FROM swap");
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
     * Get all the swaps in the database
     */
    @Override
    public Collection<Swap> values() {
    	
        Collection<Swap> col = new HashSet<Swap>();
        
        try {
            conn = Connect.connect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM swap");
            while (rs.next()) {
                
            	Instant dateCreated = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_created")));
                Instant dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
                col.add(new Swap(
                		rs.getString("id"),
                		rs.getString("bidder_id"),
                		rs.getString("taker_id"),
                		rs.getString("shift_offered_id"),
                		rs.getString("shift_wanted_id"),
                		dateCreated,
                		dateTaken,
                		rs.getBoolean("is_closed")
                ));
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