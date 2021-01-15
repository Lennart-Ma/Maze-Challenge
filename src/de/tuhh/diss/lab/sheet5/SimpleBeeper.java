package de.tuhh.diss.lab.sheet5;

import lejos.hardware.Sound;

public class SimpleBeeper implements Beeper {

	private static final int VALUE_VOLUME = 50;
	private static final int VALUE_FREQUENCY = 400;
	private static final int VALUE_DURATION = 800;
	
	public SimpleBeeper() {
		
	}
	
	
	public void playBeep() {
		Sound.setVolume(VALUE_VOLUME);
		Sound.playTone(VALUE_FREQUENCY, VALUE_DURATION);
	}

}
