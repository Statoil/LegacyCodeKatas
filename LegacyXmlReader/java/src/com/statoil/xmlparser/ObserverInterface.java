package com.statoil.xmlparser;

public interface ObserverInterface {

	/**
	 * Notifies observers that a change has taken place.
	 * 
	 * @param event takes an <code>int</code> representing the event that has 
	 * occured.
	 */
	void sendNotify(final int event);
}
