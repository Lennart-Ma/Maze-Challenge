package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroscopeTurner extends SimpleTurner implements Turner {

	private int degreesPerSecond;
	EV3GyroSensor gyrSens;

	

	public GyroscopeTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3GyroSensor gyrSens) {
		
		super(leftMotor, rightMotor);
		this.gyrSens = gyrSens;
		
		
	}

	public void setSpeed(int degreesPerSecond) {			//might be usable without declaration -> inherited from superclass
		this.degreesPerSecond = 1000*degreesPerSecond;
		getRightMotor().setSpeed(this.degreesPerSecond);
		getLeftMotor().setSpeed(this.degreesPerSecond);
	}
	
	
	public int getAngle () {
		
		
		float angleValue[] = new float[1];
				
		SampleProvider angle = gyrSens.getAngleMode();

		angle.fetchSample(angleValue, 0);
		gyrSens.close();

		
		return (int) angleValue[0];	 
	}
	
	
	public void turn(int degrees) {
		if (degrees>=0) {			
			while (getAngle() < degrees) {
				getRightMotor().backward();
				getLeftMotor().forward();
			}
		}else {
			while (getAngle() < degrees) {
				getRightMotor().forward();
				getLeftMotor().backward();
			}
		}
		getRightMotor().stop();
		getLeftMotor().stop();
	}
}
