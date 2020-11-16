package de.tuhh.diss.lab.sheet2;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Task1 {

	public static void main(String[] args) {
		String s = "Hello World";
		int x = 0;
		int z = 500;
		
		for (int i = 0; i < LCD.DISPLAY_CHAR_DEPTH; i++) {
			LCD.drawString(s, x, i);
			Delay.msDelay(z);
		}
		
	}

}
