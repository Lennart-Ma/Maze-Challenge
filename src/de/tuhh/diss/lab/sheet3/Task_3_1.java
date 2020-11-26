package de.tuhh.diss.lab.sheet3;

import lejos.hardware.lcd.LCD;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.*;

public class Task_3_1 {

	public static void main(String[] args) {

		int x = 2;
		int y = 5;
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
		
		switch (color_value) {
		
		case 7:
			LCD.drawString("BLACK", x, y);
			System.out.println("BLACK");
			break;
			
		case 2:
			LCD.drawString("BLUE", x, y);
			break;
			
		case 13:
			LCD.drawString("BROWN", x, y);
			break;
			
		case 1:
			System.out.println("GREEN");
			break;
			
		case -1:
			LCD.drawString("NONE", x, y);
			break;
			
		case 9:
			System.out.println("GRAY");
			break;
			
		case 5:
			System.out.println("ORANGE");
			break;
			
		case 0:
			System.out.println("RED");
			break;
			
		case 6:
			System.out.println("WHITE");
			break;
			
		case 3:
			System.out.println("BLACK");
			break;
		
		}
		
		
		
		
		
	}
	

}
