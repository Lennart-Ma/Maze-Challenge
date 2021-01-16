package de.tuhh.diss.lab.sheet5;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorDetector implements ColorDetect {


	public ColorDetector() {
		
	}
	
	public String getColor() {
		
		String color = "None";
		
		float[] colorId;
		colorId = new float[1];
		
		EV3ColorSensor colSens = new EV3ColorSensor(SensorPort.S1);
		SensorMode colorID = colSens.getColorIDMode();

		colorID.fetchSample(colorId, 0);
	
		int colorValue = (int) colorId[0];
				
		switch (colorValue) {
		case 7:
			color = "Black";
			break;	
		case 2:
			color = "Blue";
			break;			
		case -1:
			color = "NONE";
			break;
		case 0:
			color = "Red";
			break;		
		case 1:
			color = "Green";
			break;		
		}
		colSens.close();
		return color;

	}

}
