package schedulemanager.model;

/**
 * Class that represents a swap
 */

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Swap implements Serializable {
    
    private static final long serialVersionUID = 7526472295622776147L;
    
    private String id;
    private String bidderID; // Student that submitted the exchange offer
    private String takerID;  // Student that takes the offer       
    private String courseID; // ID of course exchanged shifts belong to
    private String shiftOfferedID;
    private String shiftWantedID;
    private Instant dateCreated; // Instant when the offer was created
    private Instant dateTaken;   // Instant when the offer was taken
    private boolean isClosed;
    
    public Swap(String bidderID, String courseID, String shiftOfferedID, String shiftWantedID) {
    	
        this.bidderID = bidderID;
        this.takerID = null;
        this.shiftOfferedID = shiftOfferedID;
        this.shiftWantedID = shiftWantedID;
        this.dateCreated = Instant.now();
        this.dateTaken = null;
        this.isClosed = false;
        
        this.id = bidderID + courseID + shiftOfferedID + shiftWantedID;
    }
    
    public Swap(Swap s){
    	this.id = s.getID();
        this.bidderID = s.getBidderID();
        this.takerID = s.getTakerID();
        this.courseID = s.getCourseID();
        this.shiftOfferedID = s.getShiftOfferedID();
        this.shiftWantedID = s.getShiftWantedID();
        this.dateCreated = s.getDateCreated();
        this.dateTaken = s.getDateTaken();
        this.isClosed = s.isClosed();
    }
    
    public String getID() { return this.id; }
    
    public String getBidderID() { return this.bidderID; }
    
    public String getTakerID() { return this.takerID; }
    
    public String getCourseID() { return this.courseID; }
    
    public String getShiftOfferedID() { return this.shiftOfferedID; }
    
    public String getShiftWantedID() { return this.shiftWantedID; }
    
    public Instant getDateCreated() { return Instant.from(this.dateCreated); }
    
    public Instant getDateTaken() { return Instant.from(this.dateTaken); }
    
    public boolean isClosed() { return this.isClosed; }
    
    public void markTaken(String takerID) {
    	
    	this.isClosed = true;
    	this.dateTaken = Instant.now();
    	this.takerID = takerID;
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("---------- Detalhes de troca ----------\n");
        s.append("ID: " + this.id + "\n");
        s.append("Proposta por: " + this.bidderID + "\n");
        s.append("Aceite por: " + this.takerID + "\n");
        s.append("Turno oferecido: " + this.courseID + " " + this.shiftOfferedID + "\n");
        s.append("Turno pretendido: " + this.courseID + " " + this.shiftWantedID + "\n");
        s.append("Date created: " + LocalDateTime.ofInstant(this.dateCreated, ZoneId.systemDefault()) + "\n");
        s.append("Date taken: " + LocalDateTime.ofInstant(this.dateTaken, ZoneId.systemDefault()) + "\n");
        s.append("Proposta aberta: " + !this.isClosed + "\n");
        s.append("---------------------------------------\n");
    
        return s.toString();
    }
    
    @Override
    public Swap clone(){
        return new Swap(this);
    }
}