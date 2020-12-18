package de.tuhh.diss.lab.sheet4;

import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;



public class Robot {
		
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
		
		// ggf. als einzelne Methode ausgliedern???
		RegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		RegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3GyroSensor gyrSens = new EV3GyroSensor(SensorPort.S3);
		
						
		int degreesPerSecond = 1000;
		int rotationAngle = -180;
		int turner_id = 1;
		 
		Turner[] turner = { new SimpleTurner(rightMotor, leftMotor), new GyroscopeTurner(rightMotor, leftMotor, gyrSens)};
		turnRobot(rotationAngle, degreesPerSecond, turner[turner_id]);

		
		Delay.msDelay(100);
		gyrSens.close();
		sim.stopSimulation();
	}

}
