package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class SimpleTurner implements Turner {
	
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private int degreesPerSecond;
	private static final int WHEELRADIUS = 54/2; //in mm
	private static final double TURNINGCIRCLE = 371.00; //in mm

	public SimpleTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
	
	
	public void setSpeed(int degreesPerSecond) {
		this.degreesPerSecond = degreesPerSecond;
		this.rightMotor.setSpeed(this.degreesPerSecond);
		this.leftMotor.setSpeed(this.degreesPerSecond);
	}

	public void turn(int rotationAngle) {
		
		int motorDegree = getMotorDegree(rotationAngle);
		
		double degreesPerSecond_double = this.degreesPerSecond;
		
		double delay_time = (Math.abs(motorDegree)/Math.abs(degreesPerSecond_double))*1000;
		
		int delay_time_int = (int)delay_time;
		
		Delay.msDelay(2000);
		
		if (rotationAngle < 0) {
			this.rightMotor.rotate(-motorDegree,true);
			this.leftMotor.rotate(motorDegree,true);
			
		}else {
			this.rightMotor.rotate(-motorDegree,true);
			this.leftMotor.rotate(motorDegree,true);
		
		}
		Delay.msDelay(delay_time_int);
		System.out.println("Done");

	}
	
	private int getMotorDegree(int rotationAngle) {
		
		double distanceWheel = ((rotationAngle* TURNINGCIRCLE)/360);
	
		double alphaWheel = (distanceWheel/(2*Math.PI*WHEELRADIUS))*360;
		
		double alphaMotor = 3*alphaWheel;
		
		int alphaMotorPerMotor = (int)alphaMotor*2;
		
		return alphaMotorPerMotor;
		
	}

}