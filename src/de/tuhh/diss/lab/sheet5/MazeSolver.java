package de.tuhh.diss.lab.sheet5;

import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import de.tuhh.diss.lab.sheet4.Robot;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MazeSolver {


	private static RegulatedMotor leftMotor;
	private static RegulatedMotor rightMotor;
	private static EV3GyroSensor gyrSens;
	private static String wantedColor;
	private static String foundColor;
	private static String foundColorFront;
	private static boolean colorFound;
	private static boolean loopOfDeath; 					//is set to false initaly since we dont think that the goal wall will be left to the starting position
	private static RobotStarter robotStarter;
	private static SimpleBeeper simpleBeeper;
	private static ColorDetector colorDetector;
	private static GyroTurner turner;
	private static Drive driver; 
	private static final int TILELENGTH = 350/2; //in mm

	
	
	private static void initializeHardware() {
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		gyrSens = new EV3GyroSensor(SensorPort.S3);
	}
	
	private static String checkWallColor() {
		driver.driveForward(-45);
		foundColor = colorDetector.getColor();			    //kein Zugriff auf turnCW oder CCW da sonst direkter Zugriff auf leftMotor,rightMotor
		driver.driveForward(45);
		return foundColor;
	}
	
	private static boolean solvingMaze() {
		
		foundColorFront = checkWallColor();							//check front wall
		colorFound = false;
		
		System.out.println("Found Color: " + foundColor);                              //state sensor value
		System.out.println("Wanted Color: " + wantedColor);                              //state sensor value

		
		if (foundColor == wantedColor) {						
			simpleBeeper.playBeep();
			colorFound = true;
		} else if(loopOfDeath) {
			System.out.println("in 1st else");                  
			turner.turn(-90,1000);								
			foundColor = checkWallColor();						//check left wall
			System.out.println("Found Color: " + foundColor);   
			
			if (foundColor == wantedColor) {
				simpleBeeper.playBeep();
				colorFound = true;
			} else if (foundColor == "NONE") {					
				driver.driveForward(-TILELENGTH);				// drives left if left Wall is not wanted Color but is None (no wall)
				loopOfDeath = true;
			} else {
				turner.turn(90,1000);							// turns right relativ, front absolut
				if (foundColorFront == "NONE") {
					driver.driveForward(-TILELENGTH);			// drives forward
					loopOfDeath = true;
				} else {
					turner.turn(90,1000);
					loopOfDeath = false;
				}
			}
		} else {
			if (foundColorFront == "NONE") {
				driver.driveForward(-TILELENGTH);
				loopOfDeath = true;
			} else {
				turner.turn(90,1000);
				loopOfDeath = false;
			}
		}
		return colorFound;
	}

	public static void main(String[] args) {
		MazebotSimulation sim = new MazebotSimulation("Mazes/Mazes_4x4_2.png", 1.4 , 1.4);
		GuiMazeVisualization gui = new GuiMazeVisualization(1.4, sim.getStateAccessor());
		sim.scaleSpeed(1);
		sim.setRobotPosition(0.175, 0.175, 90);

		sim.startSimulation();
		gui.startVisualization();
		
		
		robotStarter = new RobotStarter();
		simpleBeeper = new SimpleBeeper();
		colorDetector = new ColorDetector();
		initializeHardware();
		turner = new GyroTurner(leftMotor, rightMotor, gyrSens);
		driver = new Drive(leftMotor, rightMotor);
		
		
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
