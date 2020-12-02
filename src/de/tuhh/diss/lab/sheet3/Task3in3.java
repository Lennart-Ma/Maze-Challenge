package de.tuhh.diss.lab.sheet3;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Task3in3 {
	
	
	final static int XDISPLAY = 0;
	final static int YDISPLAY = 5;
	final static int DELAY = 5; //in milliseconds

	public static void main(String[] args) {
				
		int motorSpeed = 500; 
		float minDist = 5; //in cm
		
		approachWall(motorSpeed, minDist);
		
	}

	
	public static void approachWall(int motorSpeed, float minDist) {
		
		var rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		var leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		
		rightMotor.setSpeed(motorSpeed);
		leftMotor.setSpeed(motorSpeed);
		
		// push arrow up (=1) to move forward		
		switch (Button.waitForAnyPress()) {
		
		case 1:
			while (checkDistance() > minDist) {
				rightMotor.backward();
				leftMotor.backward();
				
				Delay.msDelay(DELAY);		
		
				displayValues(checkColor(), checkDistance());
			}
		
		}
		
		rightMotor.close();
		leftMotor.close();	
	}
	
	public static void displayValues (String color, float dist) {
			
			String distanceString = String.valueOf(dist);
	
			LCD.drawString(distanceString, XDISPLAY, YDISPLAY );
			LCD.drawString(color, XDISPLAY, YDISPLAY + 1);	
			
		}

	
	
	public static float checkDistance() {
		
		float distValue[];
		distValue = new float[1];
		float distValueCm;
		
		EV3UltrasonicSensor distSens = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider dist = distSens.getDistanceMode();

		dist.fetchSample(distValue, 0);
		
		distValueCm = distValue[0] * 100;
		
		distSens.close(); 
		
		return distValueCm;
		
	}
	
	
	public static String checkColor() {
		
		String color = "None";
		
		float[] colorId;
		colorId = new float[1];
		
		EV3ColorSensor colSens = new EV3ColorSensor(SensorPort.S1);
		SensorMode colorID = colSens.getColorIDMode();

		colorID.fetchSample(colorId, 0);
	
		int colorValue = (int) colorId[0];
				
		switch (colorValue) {
		case 7:
			color = "BLACK";
			break;	
		case 2:
			color = "BLUE";
			break;			
		case -1:
			color = "NONE";
			break;
		case 0:
			color = "RED";
			break;		
		case 3:
			color = "YELLOW";
			break;		
		}
		colSens.close();
		return color;
	}

}
