package de.tuhh.diss.lab.sheet5;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class RobotStarter implements Starter {
	
	private String menu;									// Make sure which variables can be 
	private int menuPosY;
	private String colorOne;
	private int colorOnePosY;
	private String colorTwo;
	private int colorTwoPosY;
	private String colorThree;
	private int colorThreePosY;
	private int arrowLoc;
	private boolean startRobot;
	private String wantedColor; 
	
	public RobotStarter() {
		
	}

	private void displayMenu() {
		
		String menu = "  Select a Color";
		int menuPosY = 0;
		String colorOne = "  Red ";
		int colorOnePosY = 2;
		String colorTwo = "  Green ";
		int colorTwoPosY = 3;
		String colorThree = "  Blue ";
		int colorThreePosY = 4;
		int x = 0;											// change the location where we initialize and the name of the variable x
		
		LCD.drawString(menu, x, menuPosY);
		LCD.drawString(colorOne, x, colorOnePosY);
		LCD.drawString(colorTwo, x, colorTwoPosY);
		LCD.drawString(colorThree, x, colorThreePosY);
	}
	
	
	private void printArrow(int y) {
		int x = 0;
		String arrow = ">";
		LCD.drawString(arrow, x, y);
	}
	
	
	private void deletePrevArrw(int y) {
		int x = 0;
		String noarrow = "  ";
		LCD.drawString(noarrow, x, y);
	}
		
	
	public void startRobot() {

		arrowLoc = 2;
		do {
			displayMenu();
			printArrow(arrowLoc);
			switch (Button.waitForAnyPress()) {
			case 1:
				if (arrowLoc <= 2) {
					break;
				} else {
					deletePrevArrw(arrowLoc);
					arrowLoc -= 1;
					break;			
				}
			case 4:
				if (arrowLoc >= 4) {
					break;
				} else {
					deletePrevArrw(arrowLoc);
					arrowLoc += 1;
					break;
				}
			case 2:
				switch (arrowLoc) {
				
				case 2:
					wantedColor = "Red";
					startRobot = true;
					break;
				case 3: 
					wantedColor = "Green";
					startRobot = true;
					break;
				case 4:
					wantedColor = "Blue";
					startRobot = true;
					break;
				}
			}	
		} while (startRobot == false);
	
		LCD.drawString("Started searching..", 0, 6); 			// Make it not hardcoded but with a variable
		LCD.drawString("for " + wantedColor, 0, 7);

	}


	public String getColor() {
		return wantedColor;
	}
	
}
