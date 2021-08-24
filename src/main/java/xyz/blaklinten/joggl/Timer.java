package xyz.blaklinten.joggl;

import xyz.blaklinten.joggl.Models.Entry;

public class Timer {

	private Entry entry = null;

	public void startTimer(String name, String client,
			String project, String description) throws TimerAlreadyRunningException {
		if (entry != null) {
			throw new TimerAlreadyRunningException("A timer is already running!");
		}

		entry = new Entry(name, client, project, description);
		entry.start();
	}

	public void stopTimer() throws NoActiveTimerException {
		if (entry == null){
			throw new NoActiveTimerException("No Timer to stop!");
		}
	}

	private class NoActiveTimerException extends Exception {
		public NoActiveTimerException(String errorMessage){
			super(errorMessage);
		}
	}

	private class TimerAlreadyRunningException extends Exception {
		public TimerAlreadyRunningException(String errorMessage){
			super(errorMessage);
		}
	}
}
