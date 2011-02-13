package com.statoil.xmlparser;

public interface ObservableInterface {

	/**
	 * Signal sent trough session manager when a new data set are made 
	 * available.
	 */
	int NEW_DATASET = 0;

	/** Signal sent trough session manager when a new session are opened. */
	int NEW_SESSION = 1;

	/**
	 * Signal sent trough session manager when a new set of horizons are made 
	 * available.
	 */
	int NEW_HORIZONS_AVAILIBLE = 2;

	/**
	 * Signal sent trough session manager when a new set of horizons are made 
	 * available.
	 */
	int SAVE_PERFORMED = 3;

	
	/**
	 * Observers register for the notifications.
	 * 
	 * @param obs takes an <code>ObserverInterface</code>.
	 */
	void registerInterest(ObserverInterface obs);

	/**
	 * Observers unregister for the notifications.
	 * 
	 * @param obs takes an <code>ObserverInterface</code>.
	 */
	void unregisterInterest(ObserverInterface obs);

	/**
	 * Enables or disables the stream of notifications.
	 * 
	 * @param enable takes a <code>boolean</code> true if notification stream 
	 * should be enabled and false if notification stream should be disabled.
	 */
	void enableNotifications(boolean enable);
}
