package de.tuhh.diss.lab.sheet5;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroTurner implements Turner{
	
	private static final double END = 0; 
	private final double ANGULAR_VELOCITY = 750;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private EV3GyroSensor gyrSens;
	

	public GyroTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3GyroSensor gyrSens) {
		
		this.leftMotor= leftMotor;
		this.rightMotor = rightMotor;
		this.gyrSens = gyrSens;
		Delay.msDelay(750);
		this.gyrSens.reset();
		Delay.msDelay(750);		
	}

	
	private float getAngle() {
		
		float angleValue[] = new float[1];
		SampleProvider angle = gyrSens.getAngleMode();
		angle.fetchSample(angleValue, 0);
		return Math.abs(angleValue[0]);	 
	}
	
	
	private double calcDelta (int deg) {
		
		double delta;
		
		if (deg>0) {
			delta = -getAngle() + deg;
			return delta;
		}
		else {
			delta = -getAngle() - deg;
			return delta;
		}
	}
	
	
	private void runMotors(int angularVelocityInt, boolean CW) {
		setSpeed(angularVelocityInt);
		if (CW) {
			rightMotor.forward();
			leftMotor.backward();
		} else {
			rightMotor.backward();
			leftMotor.forward();
		}
		Delay.msDelay(5);
	}
	
	public void turnCW() {
		
		int deg = -90;
		
		while (calcDelta(deg) > END) { 
			
			if (calcDelta(deg) <= 25) {
				
				double angularVelocity = ((calcDelta(deg)/55) * ANGULAR_VELOCITY);
				int angularVelocityInt = (int)angularVelocity;
				runMotors(angularVelocityInt, true);
			} else {
				
				runMotors((int)ANGULAR_VELOCITY, true);
			}	
		}

		rightMotor.stop();
		leftMotor.stop();
		gyrSens.reset();
		
	}
	
	
	public void turnCCW() {
		
		int deg = 90;
		
		while (calcDelta(deg) > END) { 
			
			if (calcDelta(deg) <= 25) {
				
				double angularVelocity = ((calcDelta(deg)/55) * ANGULAR_VELOCITY);
				int angularVelocityInt = (int)angularVelocity;
				System.out.println(angularVelocityInt);
				System.out.println(getAngle());
				runMotors(angularVelocityInt, false);
			} else {
				
				runMotors((int)ANGULAR_VELOCITY, false);
			}	
		}
		
		rightMotor.stop();
		leftMotor.stop();	
		gyrSens.reset();
		
	}
	
	
	private void setSpeed(int angluarVelocity) {
		
		rightMotor.setSpeed(angluarVelocity);
		leftMotor.setSpeed(angluarVelocity);
	}
	
}
