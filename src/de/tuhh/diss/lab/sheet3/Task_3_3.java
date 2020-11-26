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

public class Task_3_3 {

	public static void main(String[] args) {
		
		
		var rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		var leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		
		rightMotor.setSpeed(500);
		leftMotor.setSpeed(500);
		
		int x = 0;
		int y = 5;
		int y_2 = y + 1;
		
		
		float max_dist = 5; //cm
		
		float distance = checkDistance();
		
		
		switch (Button.waitForAnyPress()) {
		
		// push arrow up - move forward
		case 1:
			do {
				rightMotor.backward();
				leftMotor.backward();
				Delay.msDelay(50);
				distance = checkDistance();
				
				String distance_string = String.valueOf(distance);
				
				String color = checkColor();
		
				LCD.drawString(distance_string, x, y);
				LCD.drawString(color, x, y_2);
			} while (distance > max_dist );
		
		}
		
		rightMotor.close();
		leftMotor.close();

	}
	
	public static float checkDistance() {
		
		float dist_value[];
		dist_value = new float[1];
		float dist_value_cm;
		
		EV3UltrasonicSensor distSens = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider dist = distSens.getDistanceMode();


		dist.fetchSample(dist_value, 0);
		
		// convert dist_value to cm
		dist_value_cm = dist_value[0] * 100;
		
		distSens.close(); 
		
		return dist_value_cm;
		
	}
	
	
	public static String checkColor() {
		
		String color = "None";
		
		// create float array to store color_id
		float[] color_id;
		color_id = new float[1];
		
		//Access to the Color Identification Sensor
		EV3ColorSensor colSens = new EV3ColorSensor(SensorPort.S1);
		SensorMode colorID = colSens.getColorIDMode();

		// store colorID in the array color_id
		colorID.fetchSample(color_id, 0);
	
		// make float to int for switch case 
		int color_value = (int) color_id[0];
		
		System.out.println(color_id[0]);
		
		switch (color_value) {
		
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
