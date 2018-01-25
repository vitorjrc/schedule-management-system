package schedulemanager.model;

import java.util.*;
import java.io.Serializable;
import schedulemanager.db.*;

/**
 * Runs shift exchange functionality and keeps track of swap history
 */
public class SwapManager implements Serializable{
         
    private static final long serialVersionUID = 7526472295622776147L;

    private SwapDAO swapDAO;
	private AuthManager authManager;     // Keeps an instance of authManager to check student info
	
    public SwapManager(AuthManager authManager) {
		this.authManager = authManager;
		this.swapDAO = new SwapDAO();

		this.swapDAO.setSwapsAllowed(true);
	}
	
    public SwapManager(AuthManager authManager, boolean swapsAllowed) {
		this.authManager = authManager;
		this.swapDAO = new SwapDAO();
		
		this.swapDAO.setSwapsAllowed(swapsAllowed);
	}
	
	// Checks whether a swap offer is legal, given a bidder and the courseID & shiftID of the offered shift
	private boolean swapOfferAllowed(String bidderID, String courseID, String offeredShiftID) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			
			System.out.println("Swap offer not allowed: There is no logged in student.");
			return false;
		}
		
		Student loggedInStudent = (Student) this.authManager.getLoggedInUser();
		
		// Check that bidder is the student that is logged in
		if (!loggedInStudent.getID().equals(bidderID)) {
			
			System.out.println("Swap offer not allowed: Logged in student has ID " + loggedInStudent.getID() + ", but received bidderID " + bidderID + ".");
			return false;
		}
		
		// Check that bidder has this shift to offer
		if (!loggedInStudent.hasShift(offeredShiftID)) {
			
			System.out.println("Swap offer not allowed: Student does not have that shift to offer.");
			return false;
		}
		
		return true;
	}
    
    // Removes all shift offers of a student that offer a given shift
    private void removeSwapOffers(Student student, String shiftID) {
    	
    	this.swapDAO.removeSwapOffers(student.getID(), shiftID);
    }
	
    public HashMap<String, Swap> getAllSwaps() {
    	
    	return this.swapDAO.getSwapsMap();
    }
    
    public HashMap<String, Swap> getAllSwapsOfStudent(String studentID) {
    	
    	return this.swapDAO.getSwapsOfStudent(studentID);
    }
    
    public HashMap<String, Swap> getOpenSwaps() {
    	 
    	return this.swapDAO.getOpenSwaps();
	}
    
    public HashMap<String, Swap> getClosedSwaps() {
        
    	return this.swapDAO.getClosedSwaps();
    }
    
    public HashMap<String, Swap> getOpenSwapsOfStudent(String studentID) {
    	
    	return this.swapDAO.getOpenSwapsOfStudent(studentID);
	}
    
    public HashMap<String, Swap> getClosedSwapsOfStudent(String studentID) {
    	
    	return this.swapDAO.getClosedSwapsOfStudent(studentID);
    }
    
    public HashMap<String, Swap> getAcceptedSwaps(String studentID) {
        return this.swapDAO.getAcceptedSwaps(studentID);
    }
	
    public void lockSwaps() {
            if (this.authManager.isAdminLoggedIn()) {
                    this.swapDAO.setSwapsAllowed(false);
            }
    }

    public void unlockSwaps() {
            if (this.authManager.isAdminLoggedIn()) {
                    this.swapDAO.setSwapsAllowed(true);
            }
    }
    
    public boolean areSwapsAllowed() {
        return this.swapDAO.areSwapsAllowed();
    }

	// Creates a swap offer
	// Returns true if swap offer is created successfully, false otherwise
	public boolean createSwapOffer(String bidderID, String courseID, String offeredShiftID, String wantedShiftID) {
		
		if (!this.swapDAO.areSwapsAllowed() || !this.swapOfferAllowed(bidderID, courseID, offeredShiftID)) {
			System.out.println("Swaps are locked or attempted swap offer is not allowed.");
			return false;
		}

		// Get bidder's swaps
		HashMap<String, Swap> bidderSwaps = this.swapDAO.getOpenSwapsOfStudent(bidderID);
		
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
		
			this.swapDAO.put(newSwap.getID(), newSwap);
			
			return true;
		}
	}
	
	// Cancels a swap offer. Returns true if successful.
	public boolean cancelSwapOffer(String studentID, String swapID) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			System.out.println("Tried to cancel a swap offer but there is no logged in student.");
			return false;
		}
		
		if (!this.swapDAO.containsKey(swapID)) {
			System.out.println("Attempted to create a swap but either swapsByStudentID does not contain a student with ID " + studentID + ", or swap " + swapID + " does not exist there.");
			return false;
		}
		
		this.swapDAO.remove(swapID);
		
		return true;
	}
	
	// Registers a swap offer as taken, and deals with assigning the shifts to the students
	// courses parameter is needed to get Shift from shiftID in a Swap
	// Returns true if successful, false otherwise
	public boolean takeSwapOffer(String takerID, String swapID, CourseDAO courses, ShiftDAO shifts) {
		
		if (!this.swapDAO.containsKey(swapID)) {
			System.out.println("Tried to take a swap offer but couldn't find a swap with ID " + swapID + "\n");
			return false;
		}
		
		Swap swap = this.swapDAO.get(swapID);
		
		if (!this.isSwapTakeable(takerID, swap)) {
			return false;
		}
		
		// Exchange shifts

		swap.markTaken(takerID);
		
		Student taker = (Student) this.authManager.getLoggedInUser(); // isSwapTakeable should have checked that bidder is logged in user already
		Student bidder = this.authManager.getStudentByID(swap.getBidderID());
		
                Shift s = shifts.get(swap.getShiftOfferedID());
                String courseID = s.getCourseID();

		Shift offered = courses.get(courseID).getShift(swap.getShiftOfferedID());
		Shift wanted = courses.get(courseID).getShift(swap.getShiftWantedID());

		taker.assignShift(offered); // Assign taker to bidder's shift
		taker.removeFromShift(wanted);

		bidder.assignShift(wanted); // Assign bidder to taker's shift
		bidder.removeFromShift(offered);
		
		// Remove any swap offers by the bidder/taker that offer the shift they just exchanged
		this.removeSwapOffers(bidder, swap.getShiftOfferedID());
		this.removeSwapOffers(taker, swap.getShiftWantedID());
		
		// Update swap on database
		this.swapDAO.remove(swapID); // Should have already been removed above, double check
		this.swapDAO.put(swapID, swap);
		
		return true;
	}
	
	// Used by the controller to know whether it should display a "Take" button
	// on a given swap
	public boolean isSwapTakeable(String takerID, Swap swap) {
		
		if (!this.authManager.isStudentLoggedIn()) {
			
			//System.out.println("Swap not takeable: No student is logged in\n");
			return false;
		}
		
		Student loggedInStudent = (Student) this.authManager.getLoggedInUser();
		
		if (!loggedInStudent.getID().equals(takerID)) {
			
			//System.out.println("Swap not takeable: logged in student has ID " + loggedInStudent.getID() + " while taker has ID " + takerID + "\n");
			return false;
		}
		
		// Check that bidder and taker still have the shifts to trade
		
		Student bidder = this.authManager.getStudentByID(swap.getBidderID());
		Student taker = (Student) this.authManager.getLoggedInUser();
		
		if (bidder == null) {
			
			//System.out.println("Swap not takeable: Couldn't find student with ID of bidder (" + swap.getBidderID() + ")\n");
			return false;
			
		} else if (taker == null) {
			
			//System.out.println("Swap not takeable: Couldn't find logged in student to be the swap taker\n");
			return false;
			
		} else if (!bidder.hasShift(swap.getShiftOfferedID())) {
			
			//System.out.println("Swap not takeable: Bidder doesn't have shift " + swap.getShiftOfferedID() + " in course " + swap.getCourseID() + "\n");
			return false;
		
		} else if (!taker.hasShift(swap.getShiftWantedID())) {
		
			//System.out.println("Swap not takeable: Taker doesn't have shift " + swap.getShiftWantedID() + " in course " + swap.getCourseID() + "\n");
			return false;
			
		} else {

			return true;
		}
	}
	
	// Direct swap, without a taker. Only possible for worker students, course manager teachers, or the admin.
	// courses parameter is needed to get Shift from courseID and shiftID
	// Returns true if successful, false otherwise
	public boolean directSwap(String studentID, String courseID, String fromShiftID, String toShiftID, CourseDAO courses) {
		
		if (this.authManager.isStudentLoggedIn()) {
			
			// A student is logged in - check that their ID equals StudentID
			// and that they can direct swap
			
			Student s = (Student) this.authManager.getLoggedInUser();

			if (!s.getID().equals(studentID) || !s.canDirectSwap()) {
				
				return false;
			}
			
		} else if (this.authManager.isTeacherLoggedIn()) {
			
			// A teacher is logged in - check that they run the course with CourseID
			
			Teacher teacher = (Teacher) this.authManager.getLoggedInUser();
			
			if (teacher.getCoursesManagedID().contains(courseID)) {
				
				return false;
			}
		
		} else if (!this.authManager.isAdminLoggedIn()) {
			
			// No student, teacher, nor admin is logged in - cancel
			
			return false;
		}
		
		Student student = (Student) this.authManager.getStudentByID(studentID);

		Shift from = courses.get(courseID).getShift(fromShiftID);
		Shift to = courses.get(courseID).getShift(toShiftID);
		
		student.assignShift(to);
		student.removeFromShift(from);
		
		return true;
	}
}
