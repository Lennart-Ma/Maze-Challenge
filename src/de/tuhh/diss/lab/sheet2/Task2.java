package de.tuhh.diss.lab.sheet2;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;


//			hoch 1, runter 4, links 16, rechts 8, center 2, esc 32,

public class Task2 {

	
	
	public static void main(String[] args) {
		
		int init = 1;
		

		getarrowpos(init);



		
	}
	
	
	public static void getarrowpos(int y) {
		
		boolean condition = true;
		
		int value_volume = 50;
		int value_frequency = 400;
		int value_duration = 500;
		
		do {
			
			printmenu(value_volume, value_frequency, value_duration);
			printarrow(y);
			
			switch (Button.waitForAnyPress()) {

			case 1:
				if (y <= 1) {
					break;
				} else {
					deleteprevarrow(y);
					y -= 1;
					break;			
				}
								
				
			case 4:
				if (y >= 4) {
					break;
				} else {
					deleteprevarrow(y);
					y += 1;
					break;
				}
				
			case 16:
				if (y == 1) {
					value_volume -= 5;
					break;
				} else if (y == 2) {
					value_frequency -= 10;
					break;
				} else if (y == 3) {
					value_duration -=10;
					break;
				} else {
					break;
				}
				
			case 8:
				if (y == 1) {
					value_volume += 5;
					break;
				} else if (y == 2) {
					value_frequency += 10;
					break;
				} else if (y == 3) {
					value_duration +=10;
					break;
				} else {
					break;
				}
				
			case 2:
				if (y == 4) {
					Sound.setVolume(value_volume);
					Sound.playTone(value_frequency, value_duration);
					break;
				} else {
					break;
				}
				

			}
			
		} while (condition == true);
		
	}
	
	

	public static void printmenu(int a,int b,int c) {
		
		//int delay = 500;
		
		String menu = "        menu";
		int menu_y = 0;
		String volume = "     volume "  + Integer.toString(a);
		int volume_y = 1;
		String freq = "    frequency " + Integer.toString(b);
		int freq_y = 2;
		String dur = "     duration "  + Integer.toString(c);
		int dur_y = 3;
		String play = "     play";
		int play_y = 4;
		int x = 0;
		
		LCD.drawString(menu, x, menu_y);
		LCD.drawString(volume, x, volume_y);
		LCD.drawString(freq, x, freq_y);
		LCD.drawString(dur, x, dur_y);
		LCD.drawString(play, x, play_y);
		
		//Delay.msDelay(delay);
		
	}
	
	public static void printarrow(int y) {
		
		int x = 0;
		String arrow = ">";
		LCD.drawString(arrow, x, y);
		
	}
	
	public static void deleteprevarrow(int y) {
		
		int x = 0;
		String noarrow = "  ";
		LCD.drawString(noarrow, x, y);
	}
	
	
}
