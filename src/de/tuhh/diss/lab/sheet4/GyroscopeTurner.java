package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroscopeTurner implements Turner {

	private static final double E = 5; //in deg, epsilon 

	

	private int degreesPerSecond;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private EV3GyroSensor gyrSens;
	

	public GyroscopeTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3GyroSensor gyrSens) {
		
		this.leftMotor= leftMotor;
		this.rightMotor = rightMotor;
		this.gyrSens = gyrSens;
		Delay.msDelay(750);
		this.gyrSens.reset();
		Delay.msDelay(750);		
	}

	
	private float getAngle () {
		
		float angleValue[] = new float[1];
		SampleProvider angle = gyrSens.getAngleMode();
		angle.fetchSample(angleValue, 0);
		System.out.println(angleValue[0]);                              //state sensor value
		return Math.abs(angleValue[0]);	 
	}
	
	
	private double calcDelta (int deg) {
		
		if (deg>0) return -deg + getAngle();
		else return deg + getAngle();
	}
	
	
	private void turnCW(int deg) {
		
		rightMotor.backward();
		leftMotor.forward();
		controlTurn(deg);
	}
	
	
	private void turnCCW(int deg) {
		
		leftMotor.backward();
		rightMotor.forward();
		controlTurn(deg);
	}
	
	
	private void controlTurn(int deg) {
		
		while (calcDelta(deg) < E) { 
			Delay.msDelay(5);
			if(calcDelta(deg) > -5*E ) {                                     //5*epsilon interval set to decrease speed when reached
				setSpeed((int)0.9*this.degreesPerSecond);
			}
			if (calcDelta(deg) == 0)break;                                   //the loop is not breaking without this statement
		}
		if (calcDelta(deg)<E) {
			rightMotor.stop();
			leftMotor.stop();
			System.out.println("STOPPED MOTOR");                              //state motor state
		}
	}
	
	
	public void turn(int degrees) {

		if (degrees>0) {
			turnCCW(degrees);
			System.out.println("final angle: " + getAngle());                 //state final angle

		}
		else if(degrees<0){
			turnCW(degrees);
			System.out.println("final angle: " + -getAngle());                //state final angle

		}
	}

	
	public void setSpeed(int degreesPerSecond) {
		
		this.degreesPerSecond = degreesPerSecond;
		rightMotor.setSpeed(this.degreesPerSecond);
		leftMotor.setSpeed(this.degreesPerSecond);		
	}
}
	