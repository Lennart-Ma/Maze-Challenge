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
	private static boolean colorFound;
	private static RobotStarter robotStarter;
	private static SimpleBeeper simpleBeeper;
	private static ColorDetector colorDetector;
	private static GyroTurner turner;
	private static Drive driver; 
	
	
	private static void initializeHardware() {
		rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		gyrSens = new EV3GyroSensor(SensorPort.S3);
	}
	
	private static boolean solvingMaze() {
		
		foundColor = colorDetector.getColor();
		colorFound = false;
		
		if (foundColor == wantedColor) {
			simpleBeeper.playBeep();
			colorFound = true;
		} else {
			turner.turn(-90);								//turnCCW hardcoded, constant ?? wie machen?
			foundColor = colorDetector.getColor();			//kein Zugriff auf turnCW oder CCW da sonst direkter Zugriff auf leftMotor,rightMotor
			if (foundColor == wantedColor) {
				simpleBeeper.playBeep();
				colorFound = true;
			} else if (foundColor == "None") {
				driver.driveForward();
			} else {
				turner.turn(90);							//turnCW
				foundColor = colorDetector.getColor();
				if (foundColor == "None") {
					driver.driveForward();
				} else {
					turner.turn(90);						//turnCW
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
