package me.mani.panemgames;

import java.util.ArrayList;
import java.util.List;

public class PanemListenerManager {
	
	private static List<PanemListener> allListeners = new ArrayList<>();
	
	public static void addListener(PanemListener listener) {
		allListeners.add(listener);
	}
	
	public static void callListener(PanemEvent event) {
		for (PanemListener listener : allListeners) {
			listener.onCall(event);
		}
	}
	
	public interface PanemListener {
		
		public void onCall(PanemEvent event);
		
	}
	
	public interface PanemEvent {
		
	}

}
