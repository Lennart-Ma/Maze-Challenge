package de.tuhh.diss.lab.sheet5;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorDetector implements ColorDetect {
	
	private final int BLACK = 7;
	private final int BLUE = 2;
	private final int NONE = -1;
	private final int RED = 0;
	private final int GREEN = 1;

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
		case BLACK:
			color = "Black";
			break;	
		case BLUE:
			color = "Blue";
			break;			
		case NONE:
			color = "NONE";
			break;
		case RED:
			color = "Red";
			break;		
		case GREEN:
			color = "Green";
			break;		
		}
		colSens.close();
		return color;

	}

}
