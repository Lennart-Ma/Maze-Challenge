package de.tuhh.diss.lab.sheet5;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3GyroSensor;

public class GyroTurner implements Turner{
	
	private static final double E = 5; //in deg, epsilon 
	private final int ANGULAR_VELOCITY = 1000;
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

	
	private float getAngle () {
		
		float angleValue[] = new float[1];
		SampleProvider angle = gyrSens.getAngleMode();
		angle.fetchSample(angleValue, 0);
		return Math.abs(angleValue[0]);	 
	}
	
	
	private double calcDelta (int deg) {
		
		if (deg>0) return -deg + getAngle();
		else return deg + getAngle();
	}
	
	
	private void turnCW(int deg) {
		
		System.out.println("first step in CW");
		rightMotor.backward();
		leftMotor.forward();
		System.out.println("second step in CW");
		controlTurn(deg);
		System.out.println("third step in CW");
	}
	
	
	private void turnCCW(int deg) {
		
		System.out.println("first step in CCW");
		leftMotor.backward();
		rightMotor.forward();
		System.out.println("second step in CCW");
		controlTurn(deg);
		System.out.println("third step in CCW");
	}
	
	
	private void controlTurn(int deg) {
		
		while (calcDelta(deg) < E) { 
			Delay.msDelay(5);
			if(calcDelta(deg) > -5*E ) {                                     //5*epsilon interval set to decrease speed when reached
				setSpeed((int)0.9*ANGULAR_VELOCITY);
			}
			System.out.println("1st step in controlTurn" + calcDelta(deg));
			if (calcDelta(deg) == 0)break;                                   //the loop is not breaking without this statement
			System.out.println("2st step in controlTurn" + calcDelta(deg));
		}
		if (calcDelta(deg)<E) {
			rightMotor.stop();
			leftMotor.stop();
			gyrSens.reset();
		}
	}
	
	private void setSpeed(int angluarVelocity) {
		
		rightMotor.setSpeed(angluarVelocity);
		leftMotor.setSpeed(angluarVelocity);
		
	}
	
	
	public void turn(int degrees) {

		setSpeed(ANGULAR_VELOCITY);
		
		System.out.println("First step in turner.turn()");
		
		if (degrees>0) {
			System.out.println("Second step >0 in turner.turn()");
			turnCCW(degrees);

		}
		else if(degrees<0){
			System.out.println("Second step <0 in turner.turn()");
			turnCW(degrees);
			System.out.println("Third step <0 in turner.turn()");
		}
	}

}
