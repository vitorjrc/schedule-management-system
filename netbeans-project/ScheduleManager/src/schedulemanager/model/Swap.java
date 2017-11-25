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
    private Student bidder; // Student that submitted the exchange offer
    private Student taker;  // Student that takes the offer           
    private Shift shiftOffered;
    private Shift shiftWanted;
    private Instant dateCreated; // Instant when the offer was created
    private Instant dateTaken;   // Instant when the offer was taken
    private boolean isClosed;
    
    public Swap(Student bidder, Shift shiftOffered, Shift shiftWanted) {
        this.bidder = bidder;
        this.taker = null;
        this.shiftOffered = shiftOffered;
        this.shiftWanted = shiftWanted;
        this.dateCreated = Instant.now();
        this.dateTaken = null;
        this.isClosed = false;
        
        this.id = bidder.getID() + shiftOffered.getCourseId() + shiftOffered.getId() + shiftWanted.getId();
    }
    
    public Swap(Swap s){
    	this.id = s.getID();
        this.bidder = s.getBidder();
        this.taker = s.getTaker();
        this.shiftOffered = s.getShiftOffered();
        this.shiftWanted = s.getShiftWanted();
        this.dateCreated = s.getDateCreated();
        this.dateTaken = s.getDateTaken();
        this.isClosed = s.isClosed();
    }
    
    public String getID() { return this.id; }
    
    public Student getBidder() { return this.bidder.clone(); }
    
    public Student getTaker() { return this.taker.clone(); }
    
    public Shift getShiftOffered() { return this.shiftOffered.clone(); }
    
    public Shift getShiftWanted() { return this.shiftWanted.clone(); }
    
    public Instant getDateCreated() { return Instant.from(this.dateCreated); }
    
    public Instant getDateTaken() { return Instant.from(this.dateTaken); }
    
    public boolean isClosed() { return this.isClosed; }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("---------- Detalhes de troca ----------\n");
        s.append("ID: " + this.id + "\n");
        s.append("Proposta por: " + this.bidder + "\n");
        s.append("Aceite por: " + this.taker + "\n");
        s.append("Turno oferecido: " + this.shiftOffered.getCourseId() + " " + this.shiftOffered.getId() + "\n");
        s.append("Turno pretendido: " + this.shiftWanted.getCourseId() + " " + this.shiftWanted.getId() + "\n");
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