package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroscopeTurner implements Turner {

	private int degreesPerSecond;
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	static final double E = 5; //in deg, epsilon 
	EV3GyroSensor gyrSens;

	public GyroscopeTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor, EV3GyroSensor gyrSens) {
		
		this.leftMotor= leftMotor;
		this.rightMotor = rightMotor;
		this.gyrSens = gyrSens;
		Delay.msDelay(750);
		this.gyrSens.reset();
		Delay.msDelay(750);		
		System.out.println("initVal: " + getAngle());								//state initial sensor value
	}

	
	public float getAngle () {
		
		float angleValue[] = new float[1];
		SampleProvider angle = gyrSens.getAngleMode();
		angle.fetchSample(angleValue, 0);
		System.out.println(angleValue[0]);											//state sensor value
		return Math.abs(angleValue[0]);	 
	}
	
	
	public double calcDelta (int deg) {
		if (deg>0) return -deg + getAngle();
		else return deg + getAngle();
	}

	
	public void turn(int degrees) {

		System.out.println("deg: " + degrees); 										//state desired degree of turn
		if (degrees>0) {
			turnCCW(degrees);
		}
		else if(degrees<0){
			turnCW(degrees);
		}
		System.out.println("final angle" + getAngle());								//state final angle
	}

	
	public void setSpeed(int degreesPerSecond) {
		
		this.degreesPerSecond = degreesPerSecond;
		rightMotor.setSpeed(this.degreesPerSecond);
		leftMotor.setSpeed(this.degreesPerSecond);		
	}

	
	public void turnCW(int deg) {
		
		leftMotor.backward();
		rightMotor.forward();
		while (calcDelta(deg) < E) {
			Delay.msDelay(5);
			System.out.println("deltaN: " + calcDelta(deg)); 						//state delta
			if(calcDelta(deg) > -5*E){
				setSpeed((int)0.9*this.degreesPerSecond);
				System.out.println("speed: " + this.degreesPerSecond);				//state regulated speed
			}
			if (calcDelta(deg)==0)break;
		}
		if (calcDelta(deg)<E) {
			rightMotor.stop();
			leftMotor.stop();
			System.out.println("STOPPED MOTOR");									//state motor state
		}
	}
	
	public void turnCCW(int deg) {

		rightMotor.backward();
		leftMotor.forward();
		while (calcDelta(deg) < E) { 
			Delay.msDelay(5);
			System.out.println("deltaP: " + calcDelta(deg));						//state delta
			if(calcDelta(deg) > -5*E ) {
				setSpeed((int)0.9*this.degreesPerSecond);
				System.out.println("speed: " + this.degreesPerSecond);				//state regulated speed -> 0???
			}
			if (calcDelta(deg) == 0)break;
		}
		if (calcDelta(deg)<E) {
			rightMotor.stop();
			leftMotor.stop();
			System.out.println("STOPPED MOTOR");									//state motor state
		}
	}
}
	
