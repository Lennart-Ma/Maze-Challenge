package de.tuhh.diss.lab.sheet5;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Drive implements Driver{
	
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private static final int WHEELDIAMETER = 54; //in mm
	private static final int ANGULARVELOCITY = 1500;
	
	public Drive(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
	

	public void driveForward(int length) {
		
		int motorDegree = setMotorDegree(length);
		setSpeed();
		double degreesPerSecond_double = ANGULARVELOCITY;
		double delay_time = (Math.abs(motorDegree)/Math.abs(degreesPerSecond_double))*1000;
		int delay_time_int = (int)delay_time;
		
		this.rightMotor.rotate(motorDegree,true);
		this.leftMotor.rotate(motorDegree,true);

		Delay.msDelay(4 * delay_time_int);
	}
	
	
	private int setMotorDegree(int length) {
		
		double alphaWheel = (length/(Math.PI * WHEELDIAMETER)) *360;
		double alphaMotor = 3*alphaWheel;
		int alphaMotorPerMotor = (int)alphaMotor*2;
		
		return alphaMotorPerMotor;
	}
	
	
	public void setSpeed() {
		this.rightMotor.setSpeed(ANGULARVELOCITY);
		this.leftMotor.setSpeed(ANGULARVELOCITY);
	}
}


