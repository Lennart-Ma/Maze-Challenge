package de.tuhh.diss.lab.sheet5;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class RobotStarter implements Starter {
	
	private boolean startRobot;
	private String wantedColor; 
	
	public RobotStarter() {
		
	}

	private void displayMenu() {
		
		String menu = "  Select a Color";
		String colorOne = "  Red ";
		String colorTwo = "  Green ";
		String colorThree = "  Blue ";
		int menuPosY = 0;
		int colorOnePosY = 2;
		int colorTwoPosY = 3;
		
		int colorThreePosY = 4;
		int xPosition = 0;											// change the location where we initialize and the name of the variable x
		
		LCD.drawString(menu, xPosition, menuPosY);
		LCD.drawString(colorOne, xPosition, colorOnePosY);
		LCD.drawString(colorTwo, xPosition, colorTwoPosY);
		LCD.drawString(colorThree, xPosition, colorThreePosY);
	}
	
	
	private void printArrow(int yPos) {
		
		int xArrowPos = 0;
		String arrow = ">";
		
		LCD.drawString(arrow, xArrowPos, yPos);
	}
	
	
	private void deletePrevArrw(int yPos) {
		int xPosArrow = 0;
		String noarrow = "  ";
		LCD.drawString(noarrow, xPosArrow, yPos);
	}
		
	
	public void startRobot() {

		int arrowLoc = 2;
		
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
	
		LCD.drawString("Started searching..", 0, 6); 
		LCD.drawString("for " + wantedColor, 0, 7);

	}


	public String getWantedColor() {
		return wantedColor;
	}
	
}
