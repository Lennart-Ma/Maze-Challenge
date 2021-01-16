package de.tuhh.diss.lab.sheet5;

import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import lejos.utility.Delay;

public class MazeSolver {
	
	private static String wantedColor;
	private static String foundColor;
	private static boolean colorFound;
	private static RobotStarter robotStarter;
	private static SimpleBeeper simpleBeeper;
	private static ColorDetector colorDetector;
	private static GyroTurner turner;
	private static ForwardDriver driver; 
	
	
	private static boolean solvingMaze() {
		
		foundColor = colorDetector.getColor();
		colorFound = false;
		
		if (foundColor == wantedColor) {
			simpleBeeper.playBeep();
			colorFound = true;
		} else {
			turner.turnCCW();
			foundColor = colorDetector.getColor();
			if (foundColor == wantedColor) {
				simpleBeeper.playBeep();
				colorFound = true;
			} else if (foundColor == "None") {
				driver.driveForward();
			} else {
				turner.turnCW();
				foundColor = colorDetector.getColor();
				if (foundColor == "None") {
					driver.driveForward();
				} else {
					turner.turnCW();
				}
			}
		}
		return colorFound;
	}

	public static void main(String[] args) {
		MazebotSimulation sim = new MazebotSimulation("Mazes/TestArea.png", 1.8 , 1.45);
		GuiMazeVisualization gui = new GuiMazeVisualization(1.5, sim.getStateAccessor());
		sim.scaleSpeed(1);
		sim.setRobotPosition(0.75, 0.75, 90);

		sim.startSimulation();
		gui.startVisualization();
		
		
		robotStarter = new RobotStarter();
		simpleBeeper = new SimpleBeeper();
		colorDetector = new ColorDetector();
		
		robotStarter.startRobot();
		simpleBeeper.playBeep();

		wantedColor = robotStarter.getColor();
		
		while (colorFound == false) {
			colorFound = solvingMaze();
		}
		
		
		Delay.msDelay(100);
		sim.stopSimulation();

	}

}
