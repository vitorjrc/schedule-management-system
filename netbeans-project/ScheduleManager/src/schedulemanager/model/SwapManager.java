package schedulemanager.model;

import java.util.HashMap;

/**
 * Runs shift exchange functionality and keeps track of swap history
 */
public class SwapManager {
	
	/* We store swaps in a HashMap that links each student ID
	 * to another HashMap, which in turn links swap IDs to Swaps.
	 *
	 * For simplicity, closed swaps are not stored separately from
	 * open swaps. The slight efficiency gain in separating them would
	 * not be worth the increase in complexity.
	 */
	private HashMap<String, HashMap<String, Swap>> swapsByStudentID; // studentID -> (swapID -> Swap)
	
	private boolean swapsAllowed = true; // Keeps track of whether Swaps are still allowed.
	
	public SwapManager() {
		this.swapsByStudentID = new HashMap<String, HashMap<String, Swap>>();
	}
	
	public SwapManager(boolean swapsAllowed) {
		this.swapsByStudentID = new HashMap<String, HashMap<String, Swap>>();
		this.swapsAllowed = swapsAllowed;
	}

	// Creates a swap offer without checking if the student has the shift
	// they are offering. This check should be done beforehand.
	// Returns true if swap offer is created successfully, false otherwise
	public boolean createSwapOffer(String bidderID, String courseID, String offeredShiftID, String wantedShiftID) {
		
		// Create student swaps HashMap if it doesn't exist yet
		if (!this.swapsByStudentID.containsKey(bidderID)) {
			
			this.swapsByStudentID.put(bidderID, new HashMap<String, Swap>());
		}
		
		// Create new Swap
		Swap newSwap = new Swap(bidderID, courseID, offeredShiftID, wantedShiftID);
		
		// Get bidderSwaps HashMap
		HashMap<String, Swap> bidderSwaps = this.swapsByStudentID.get(bidderID);
		
		// Check that this offer doesn't already exist
		if (bidderSwaps.containsKey(newSwap.getID())) {
			
			return false; // Swap already exists, abandon
		
		} else {
		
			bidderSwaps.put(newSwap.getID(), newSwap);
			
			return true;
		}
	}
	
	// Cancels a swap offer. Returns true if successful.
	public boolean cancelSwapOffer(String studentID, String swapID) {
		
		if (!this.swapsByStudentID.containsKey(studentID)) {
			return false;
		}
		
		HashMap<String, Swap> swaps = this.swapsByStudentID.get(studentID);
		
		if (!swaps.containsKey(swapID)) {
			return false;
		}
		
		swaps.remove(swapID);
		return true;
	}
	
	// Registers a swap offer as taken by a student.
	// Doesn't deal with actually registering and
	// unregistering the students from the shifts.
	public void takeSwapOffer(String takerID, String swapID) {
		
		
	}
}
