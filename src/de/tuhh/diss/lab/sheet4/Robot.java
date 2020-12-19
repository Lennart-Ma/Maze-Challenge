package de.tuhh.diss.lab.sheet4;

import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.RegulatedMotor;



public class Robot {
	
	private static RegulatedMotor leftMotor;
	private static RegulatedMotor rightMotor;
	private static EV3GyroSensor gyrSens;
	private static int degreesPerSecond;
	private static int rotationAngle;
	private static int turner_id;
	
	private static void initializeHardware() {
		Robot.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		Robot.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		Robot.gyrSens = new EV3GyroSensor(SensorPort.S3);
	}
	
	private static void setParameter(int degreesPerSecond, int rotationAngle, int turner_id) {
		Robot.degreesPerSecond = degreesPerSecond;
		Robot.rotationAngle = rotationAngle;
		Robot.turner_id = turner_id;		
	}
		
	public static void turnRobot(int degrees, int degreesPerSecond, Turner t) {
		t.setSpeed(degreesPerSecond);
		t.turn(degrees);
	}

	public static void main(String[] args) {
		MazebotSimulation sim = new MazebotSimulation("Mazes/TestArea.png", 1.8 , 1.45);
		GuiMazeVisualization gui = new GuiMazeVisualization(1.5, sim.getStateAccessor());
		sim.scaleSpeed(1);
		sim.setRobotPosition(0.75, 0.75, 90);

		sim.startSimulation();
		gui.startVisualization();
		
		initializeHardware();
		setParameter(1000,-90,1);                                                                      // set turner_id to 0 to use SimpleTurner, 1 to use GyroTurner
		Turner[] turner = { new SimpleTurner(rightMotor, leftMotor),
		                    new GyroscopeTurner(rightMotor, leftMotor, gyrSens)};
		turnRobot(rotationAngle, degreesPerSecond, turner[turner_id]);
		
		Delay.msDelay(100);
		gyrSens.close();
		sim.stopSimulation();
	}

}
