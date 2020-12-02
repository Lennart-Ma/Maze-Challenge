package de.tuhh.diss.lab.sheet3;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Task3in2 {

	public static void main(String[] args) {
		
		
		float dist_value[];
		dist_value = new float[1];
		float dist_value_cm;
		
		EV3UltrasonicSensor distSens = new EV3UltrasonicSensor(SensorPort.S4);
		SampleProvider dist = distSens.getDistanceMode();


		dist.fetchSample(dist_value, 0);
		
		// convert dist_value to cm
		dist_value_cm = dist_value[0] * 100;
		
		System.out.println(dist_value_cm);
		
	}

}
