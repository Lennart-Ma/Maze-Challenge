package de.tuhh.diss.lab.sheet5;


import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MazeSolver {

	private static RegulatedMotor leftMotor;
	private static RegulatedMotor rightMotor;
	private static RobotStarter robotStarter;
	private static SimpleBeeper simpleBeeper;
	private static ColorDetector colorDetector;
	private static GyroTurner turner;
	private static TileDriver driver;
	private static String wantedColor;
	private static String foundColor;
	private static String foundColorFront;
	private static boolean colorFound;
	private static boolean loopOfDeath; 					
	
	
	
	private static void initializeHardware() {
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		foundColorFront = foundColor = "NONE";
	}
	
	private static String checkWallColor() {
		
		driver.approachTileEdge(true);
		foundColor = colorDetector.getColor();
		
		if (foundColor != "NONE") {
			driver.adjustTileEdgeDist();
		} else {
			driver.approachTileEdge(false);
		}
		
		return foundColor;
	}
	
	private static boolean solvingMaze() {
				
		foundColorFront = checkWallColor();						//check front wall
		colorFound = false;
	
		if (foundColor == wantedColor) {						
			simpleBeeper.playBeep();
			colorFound = true;
		} else if(loopOfDeath) {
			turner.turnCCW();
			foundColor = checkWallColor();						//check left wall	
			if (foundColor == wantedColor) {
				simpleBeeper.playBeep();
				colorFound = true;
			} else if (foundColor == "NONE") {				
				driver.driveTileForward();						// drives left if left Wall is not wanted Color but is None (no wall)
				loopOfDeath = true;
			} else {									
				turner.turnCW();								// turns right relativly, front absolut
				if (foundColorFront == "NONE") {
					driver.driveTileForward();					// drives forward
					loopOfDeath = true;
				} else {
					turner.turnCW();
					loopOfDeath = false;
				}
			}
		} else {
			if (foundColorFront == "NONE") {
				driver.driveTileForward();
				loopOfDeath = true;
			} else {
				turner.turnCW();
				loopOfDeath = false;
			}
		}
		return colorFound;
	}

	public static void main(String[] args) {
		MazebotSimulation sim = new MazebotSimulation("Mazes/Mazes_4x4_1.png", 1.4 , 1.4);
		GuiMazeVisualization gui = new GuiMazeVisualization(1.4, sim.getStateAccessor());
		sim.scaleSpeed(1);
		sim.setRobotPosition(0.175, 0.175, 90);

		sim.startSimulation();
		gui.startVisualization();
		
		
		robotStarter = new RobotStarter();
		simpleBeeper = new SimpleBeeper();
		colorDetector = new ColorDetector();
		
		initializeHardware();
		
		turner = new GyroTurner(leftMotor, rightMotor);
		driver = new TileDriver(leftMotor, rightMotor);
		robotStarter.startRobot();
		simpleBeeper.playBeep();
		wantedColor = robotStarter.getWantedColor();
		

		while (colorFound == false) {
			colorFound = solvingMaze();
		}
		

		Delay.msDelay(100);
		sim.stopSimulation();

	}

}
