package schedulemanager.model;

import java.util.HashMap;

/**
 * Runs shift exchange functionality and keeps track of swap history
 */
public class SwapManager {
	
	private HashMap<String, Swap> allSwaps;
	private HashMap<String, Swap> openSwaps;
	private HashMap<String, Swap> closedSwaps;

}
