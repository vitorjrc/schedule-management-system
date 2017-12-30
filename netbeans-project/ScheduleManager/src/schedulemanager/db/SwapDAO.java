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
                
                Instant dateTaken = null;
                if (rs.getString("date_taken") != null) 
                    dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
                    // Instant dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
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
     * Get all swaps of a given student
     */
    public HashMap<String, Swap> getSwapsOfStudent(String studentID) {
    	
        HashMap<String, Swap> s = new HashMap<String, Swap>();
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM swap WHERE bidder_id=?");
            stm.setString(1, studentID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	
                Instant dateCreated = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_created")));
                
                Instant dateTaken = null;
                if (rs.getString("date_taken") != null) 
                    dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
                    // Instant dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
                s.put(rs.getString("id"), new Swap(
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
        } finally {
            Connect.close(conn);
        }
        
        return s;
    }
    
    /**
     * Auxiliary: Get all open or closed swaps - open if open is true, closed if false
     */
    private HashMap<String, Swap> getOpenOrClosedSwaps(boolean open) {
    	
    	HashMap<String, Swap> s = new HashMap<String, Swap>();
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM swap WHERE is_closed = ?");

            if (open) {
            	stm.setInt(1, 0);
            } else {
            	stm.setInt(1, 1);
            }
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
            	
                Instant dateCreated = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_created")));
                
                Instant dateTaken = null;
                if (rs.getString("date_taken") != null) {
                    dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                }
                
                s.put(rs.getString("id"), new Swap(
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
        } finally {
            Connect.close(conn);
        }
        
        return s;
    }
    
    public HashMap<String, Swap> getOpenSwaps() {
    	return this.getOpenOrClosedSwaps(true);
    }
    
    public HashMap<String, Swap> getClosedSwaps() {
    	return this.getOpenOrClosedSwaps(false);
    }
    
    /**
     * Auxiliary - if open is true, returns all open swaps of a student, else returns all closed swaps
     */
    private HashMap<String, Swap> getOpenOrClosedSwapsOfStudent(String studentID, boolean open) {
    	
        HashMap<String, Swap> s = new HashMap<String, Swap>();
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM swap WHERE bidder_id = ? AND is_closed = ?");
            stm.setString(1, studentID);
            
            if (open) {
            	stm.setInt(2, 0);
            } else {
            	stm.setInt(2, 1);
            }
            
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
            	
                Instant dateCreated = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_created")));
                
                Instant dateTaken = null;
                
                if (rs.getString("date_taken") != null) {
                    dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
            	}
                
            	s.put(rs.getString("id"), new Swap(
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
        } finally {
            Connect.close(conn);
        }
        
        return s;
    }
    
    public HashMap<String, Swap> getOpenSwapsOfStudent(String studentID) {
    	return this.getOpenOrClosedSwapsOfStudent(studentID, true);
    }
    
    public HashMap<String, Swap> getClosedSwapsOfStudent(String studentID) {
    	return this.getOpenOrClosedSwapsOfStudent(studentID, false);
    }
    
    /**
     * Remove all open swap offers from a student that offer a given shift
     */
    public void removeSwapOffers(String studentID, String shiftID) {
    	
    	try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM swap WHERE bidder_id = ? AND shift_offered_id = ? AND is_closed = 0");
            stm.setString(1, studentID);
            stm.setString(2, shiftID);
            stm.executeUpdate();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage()); 
        } finally {
            Connect.close(conn);
        }
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
            
            Instant dateTaken = value.getDateTaken();
            
            if (dateTaken == null) {
            	stm.setString(7, null);
            } else {
            	stm.setString(7, String.valueOf(value.getDateTaken().toEpochMilli()));
            }
            
            if (value.isClosed()) {
            	stm.setInt(8, 1);
            } else {
            	stm.setInt(8, 0);
            }

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
            PreparedStatement stm = conn.prepareStatement("delete from swap where id = ?");
            stm.setString(1, key.toString());
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
                
                Instant dateTaken = null;
                if (rs.getString("date_taken") != null) 
                    dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
                //Instant dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                
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
    
    public HashMap<String, Swap> getSwapsMap() {
    	
    	Collection<Swap> swaps = this.values();
    	
    	HashMap<String, Swap> map = new HashMap<String, Swap>();
    	
    	for (Swap s : swaps) {
    		
    		map.put(s.getID(), s);
    	}
    	
    	return map;
    }
    
    /**
     * Get swaps accepted by a given student
     */
    public HashMap<String, Swap> getAcceptedSwaps(String studentID) {

    	HashMap<String, Swap> s = new HashMap<String, Swap>();
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM swap WHERE taker_id = ?");
            stm.setString(1, studentID);
            
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	
                Instant dateCreated = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_created")));
                
                Instant dateTaken = null;
                
                if (rs.getString("date_taken") != null) {
                    dateTaken = Instant.ofEpochMilli(Long.parseLong(rs.getString("date_taken")));
                }

                s.put(rs.getString("id"), new Swap(
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
        } finally {
            Connect.close(conn);
        }
        
        return s;
    }
    
    public boolean areSwapsAllowed() {
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("SELECT value FROM config WHERE name = \"swaps_allowed\"");

            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	
                return (rs.getInt("value") != 0);    
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }

        return true; // Allowed by default
    }
    
    public void setSwapsAllowed(boolean allowed) {
        
        try {
            conn = Connect.connect();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO config (name, value) VALUES (\"swaps_allowed\", ?) ON DUPLICATE KEY UPDATE value=?");
            
            if (allowed) {
            	
            	stm.setInt(1, 1);
            	stm.setInt(2, 1);
            
            } else {
            	
            	stm.setInt(1, 0);
            	stm.setInt(2, 0);
            }
            
            stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Connect.close(conn);
        }
    }
}