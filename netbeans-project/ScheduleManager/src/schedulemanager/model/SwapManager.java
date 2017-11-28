package schedulemanager.model;

import java.util.HashMap;
import java.io.Serializable;

/**
 * Runs shift exchange functionality and keeps track of swap history
 */
public class SwapManager implements Serializable{
	
	/* We store swaps in a HashMap that links each student ID
	 * to another HashMap, which in turn links swap IDs to Swaps.
	 *
	 * For simplicity, closed swaps are not stored separately from
	 * open swaps. The slight efficiency gain in separating them would
	 * not be worth the increase in complexity.
	 */
         
        private static final long serialVersionUID = 7526472295622776147L;
         
	private HashMap<String, HashMap<String, Swap>> swapsByStudentID; // studentID -> (swapID -> Swap)
	private boolean swapsAllowed;        // Keeps track of whether Swaps are still allowed.
	private AuthManager authManager;     // Keeps an instance of authManager to check student info
	
	
        public SwapManager(AuthManager authManager) {
		this.authManager = authManager;
		this.swapsByStudentID = new HashMap<String, HashMap<String, Swap>>();
                this.swapsAllowed = true;
	}
	
	
        public SwapManager(AuthManager authManager, boolean swapsAllowed) {
		this.authManager = authManager;
		this.swapsByStudentID = new HashMap<String, HashMap<String, Swap>>();
		this.swapsAllowed = swapsAllowed;
	}
	
	// Checks whether a swap offer is legal, given a bidder and the courseID & shiftID of the offered shift
	private boolean swapOfferAllowed(String bidderID, String courseID, String offeredShiftID) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			
			System.out.println("Swap offer not allowed: There is no logged in student.");
			return false;
		}
		
		Student loggedInStudent = (Student) this.authManager.getLoggedInUser();
		
		// Check that bidder is the student that is logged in
		if (loggedInStudent.getID() != bidderID) {
			
			System.out.println("Swap offer not allowed: Logged in student has ID " + loggedInStudent.getID() + ", but received bidderID " + bidderID + ".");
			return false;
		}
		
		// Check that bidder has this shift to offer
		if (!loggedInStudent.hasShift(courseID, offeredShiftID)) {
			
			System.out.println("Swap offer not allowed: Student does not have that shift to offer.");
			return false;
		}
		
		return true;
	}
	
	// Creates a given student's HashMap of swaps if it doesn't exist
	private void createStudentSwapsMapIfNotExists(String studentID) {
		
		if (!this.swapsByStudentID.containsKey(studentID)) {
			
			this.swapsByStudentID.put(studentID, new HashMap<String, Swap>());
		}
	}
    
    // Auxiliary function
    // Returns all open swaps if parameter is true,
    // all closed swaps if parameter is false
    private HashMap<String, Swap> getSwapsWithCondition(boolean openSwaps) {
    	
        HashMap<String, Swap> swaps = new HashMap<String, Swap>();
		
		for (HashMap<String, Swap> swapsOfAStudent: this.swapsByStudentID.values()) {
			
			for (Swap swap: swapsOfAStudent.values()) {
				
				if ((openSwaps && !swap.isClosed()) || (!openSwaps && swap.isClosed())) {
					
					swaps.put(swap.getID(), swap.clone());
				}
			}
		}
		
		return swaps;
    }
    
    // Auxiliary function
    // Returns open swaps of a student if parameter is true,
    // closed swaps if parameter is false
    private HashMap<String, Swap> getSwapsOfStudent(String studentID, boolean openSwaps) {
        
    	HashMap<String, Swap> swaps = new HashMap<String, Swap>();
		
		for (Swap swap: this.swapsByStudentID.get(studentID).values()) {
			
			if ((openSwaps && !swap.isClosed()) || (!openSwaps && swap.isClosed())) {
				
				swaps.put(swap.getID(), swap.clone());
			}
		}
		
		return swaps;
    }
    
    // Removes all shift offers of a student that offer a given shift
    private void removeSwapOffers(Student student, String shiftID) {
    	
    	HashMap<String, Swap> studentSwaps = this.swapsByStudentID.get(student.getID());
    	
    	for(Swap s: studentSwaps.values()) {
    	
    		if (s.getShiftOfferedID().equals(shiftID)) {
    			
    			this.swapsByStudentID.remove(s.getID());
    		}
    	}
    }
	
    public HashMap<String, Swap> getAllSwaps() {
    	
    	HashMap<String, Swap> allSwaps = new HashMap<String, Swap>();
    	
    	for (HashMap<String, Swap> swapsOfAStudent: this.swapsByStudentID.values()) {
    		
    		HashMap<String, Swap> clone = new HashMap<String, Swap>(swapsOfAStudent);
    		
    		allSwaps.putAll(clone);
    	}
    	
    	return allSwaps;
    }
    
    public HashMap<String, Swap> getAllSwapsOfStudent(String studentID) {
    	
    	return new HashMap<String, Swap>(this.swapsByStudentID.get(studentID));
    }
    
    public HashMap<String, Swap> getOpenSwaps() {
    	 
    	return new HashMap<String, Swap>(this.getSwapsWithCondition(true));
	}
    
    public HashMap<String, Swap> getClosedSwaps() {
        
    	return new HashMap<String, Swap>(this.getSwapsWithCondition(false));
    }
    
    public HashMap<String, Swap> getOpenSwapsOfStudent(String studentID) {
    	
    	return new HashMap<String, Swap>(this.getSwapsOfStudent(studentID, true));
	}
    
    public HashMap<String, Swap> getClosedSwapsOfStudent(String studentID) {
    	
    	return new HashMap<String, Swap>(this.getSwapsOfStudent(studentID, false));
    }
	
	public void lockSwaps() {
		if (this.authManager.isAdminLoggedIn()) {
			this.swapsAllowed = false;
		}
	}
	
	public void unlockSwaps() {
		if (this.authManager.isAdminLoggedIn()) {
			this.swapsAllowed = true;
		}
	}

	// Creates a swap offer
	// Returns true if swap offer is created successfully, false otherwise
	public boolean createSwapOffer(String bidderID, String courseID, String offeredShiftID, String wantedShiftID) {
		
		if (!this.swapsAllowed || !this.swapOfferAllowed(bidderID, courseID, offeredShiftID)) {
			System.out.println("Swaps are locked or attempted swap offer is not allowed.");
			return false;
		}
		
		// Create this student's swaps HashMap if it doesn't exist yet
		this.createStudentSwapsMapIfNotExists(bidderID);
		
		// Get bidder's swaps
		HashMap<String, Swap> bidderSwaps = this.swapsByStudentID.get(bidderID);
		
		if (bidderSwaps == null) {
			System.out.println("swapsByStudentID does not contain a student with ID " + bidderID);
			return false;
		}
		
		Swap newSwap = new Swap(bidderID, courseID, offeredShiftID, wantedShiftID);
		
		// Check that this offer doesn't already exist
		if (bidderSwaps.containsKey(newSwap.getID())) {
			
			System.out.println("Attempted to create a swap that already exists.");
			return false;
		
		} else {
		
			bidderSwaps.put(newSwap.getID(), newSwap);
			
			return true;
		}
	}
	
	// Cancels a swap offer. Returns true if successful.
	public boolean cancelSwapOffer(String studentID, String swapID) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			System.out.println("Tried to cancel a swap offer but there is no logged in student.");
			return false;
		}
		
		HashMap<String, Swap> swaps = this.swapsByStudentID.get(studentID);
		
		if (swaps == null || !swaps.containsKey(swapID)) {
			System.out.println("Attempted to create a swap but either swapsByStudentID does not contain a student with ID " + studentID + ", or swap " + swapID + " does not exist there.");
			return false;
		}
		
		swaps.remove(swapID);
		return true;
	}
	
	// Registers a swap offer as taken, and deals with assigning the shifts to the students
	// courses parameter is needed to get Shift from shiftID in a Swap
	// Returns true if successful, false otherwise
	public boolean takeSwapOffer(String takerID, String swapID, HashMap<String, Course> courses) {
		
		// Find the swap
		Swap swap = null;
		
		for (HashMap<String, Swap> swaps: this.swapsByStudentID.values()) { // Loop through each student's swap offers
			
			swap = swaps.get(swapID);
			
			if (swap != null) {
				break;
			}
		}
		
		if (swap == null) {
			System.out.println("Tried to take a swap offer but couldn't find a swap with ID " + swapID + "\n");
			return false;
		}
		
		if (!this.isSwapTakeable(takerID, swap)) {
			return false;
		}
		
		// Exchange shifts

		swap.markTaken(takerID);
		
		Student taker = (Student) this.authManager.getLoggedInUser(); // isSwapTakeable should have checked that bidder is logged in user already
		Student bidder = this.authManager.getStudentByID(swap.getBidderID());
		
		String courseID = swap.getCourseID();

		Shift offered = courses.get(courseID).getShift(swap.getShiftOfferedID());
		Shift wanted = courses.get(courseID).getShift(swap.getShiftWantedID());

		taker.assignShift(offered); // Assign taker to bidder's shift
		taker.removeFromShift(wanted);

		bidder.assignShift(wanted); // Assign bidder to taker's shift
		bidder.removeFromShift(offered);
		
		// Remove any swap offers by the bidder/taker that offer the shift they just exchanged
		this.removeSwapOffers(bidder, swap.getShiftOfferedID());
		this.removeSwapOffers(taker, swap.getShiftWantedID());
		
		return true;
	}
	
	// Used by the controller to know whether it should display a "Take" button
	// on a given swap
	public boolean isSwapTakeable(String takerID, Swap swap) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			
			System.out.println("Swap not takeable: No student is logged in\n");
			return false;
		}
		
		Student loggedInStudent = (Student) this.authManager.getLoggedInUser();
		
		if (loggedInStudent.getID() != takerID) {
			
			System.out.println("Swap not takeable: logged in student has ID " + loggedInStudent.getID() + " while taker has ID " + takerID + "\n");
			return false;
		}
		
		// Check that bidder and taker still have the shifts to trade
		
		Student bidder = this.authManager.getStudentByID(swap.getBidderID());
		Student taker = this.authManager.getStudentByID(swap.getTakerID());
		
		if (!bidder.hasShift(swap.getCourseID(), swap.getShiftOfferedID())) {
			
			System.out.println("Swap not takeable: Bidder doesn't have shift " + swap.getShiftOfferedID() + " in course " + swap.getCourseID() + "\n");
			return false;
		
		} else if (!taker.hasShift(swap.getCourseID(), swap.getShiftWantedID())) {
		
			System.out.println("Swap not takeable: Taker doesn't have shift " + swap.getShiftWantedID() + " in course " + swap.getCourseID() + "\n");
			return false;
			
		} else {

			return true;
		}
	}
	
	// Direct swap, without a taker. Only possible for worker students.
	// courses parameter is needed to get Shift from courseID and shiftID
	// Returns true if successful, false otherwise
	public boolean directSwap(String studentID, String courseID, String fromShiftID, String toShiftID, HashMap<String, Course> courses) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			return false;
		}
		
		Student student = (Student) this.authManager.getLoggedInUser();
		
		// Check that swapper student is the one who is logged in
		if (!studentID.equals(student.getID())) { return false; }
		
		// Check that student can direct swap
		if (!student.canDirectSwap()) { return false; }
		
		Shift from = courses.get(courseID).getShift(fromShiftID);
		Shift to = courses.get(courseID).getShift(toShiftID);
		
		student.assignShift(to);
		student.removeFromShift(from);
		
		return true;
	}
        
        public void setSwaps(HashMap<String, HashMap<String, Swap>> newMap) {
                
                this.swapsByStudentID = newMap;
        } 
}
