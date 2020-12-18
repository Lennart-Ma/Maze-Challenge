package de.tuhh.diss.lab.sheet4;

import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class SimpleTurner implements Turner {
	
	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private int degreesPerSecond;
	private static final int WHEELRADIUS = 54/2; //in mm
	private static final double TURNINGCIRCLE = 397.41; //in mm

	public SimpleTurner(RegulatedMotor leftMotor, RegulatedMotor rightMotor) {
		this.leftMotor= leftMotor;
		this.rightMotor = rightMotor;
	}
	
	
	public void setSpeed(int degreesPerSecond) {
		this.degreesPerSecond = 1000*degreesPerSecond;
		rightMotor.setSpeed(this.degreesPerSecond);
		leftMotor.setSpeed(this.degreesPerSecond);
	}

	public void turn(int rotationAngle) {
		
		int motorDegree = getMotorDegree(rotationAngle);
			
		int delay_time = (int)((motorDegree/degreesPerSecond)*1000);
		System.out.println(delay_time);
		
		Delay.msDelay(2000);
		
		if (rotationAngle < 0) {
			rightMotor.rotate(-motorDegree,true);
			leftMotor.rotate(motorDegree,true);
			
		}else {
			rightMotor.rotate(motorDegree,true);
			leftMotor.rotate(-motorDegree,true);
		}
		Delay.msDelay(delay_time);
		System.out.println("Done");

	}
	
	private int getMotorDegree(int circleDegree) {
		
		System.out.println(circleDegree);
		double distanceWheel = ((circleDegree* TURNINGCIRCLE)/360);
		System.out.println(distanceWheel);
		
		double alphaWheel = (distanceWheel/(2*Math.PI*WHEELRADIUS))*360;
		System.out.println(alphaWheel);
		
		double alphaMotor = 3*alphaWheel;
		System.out.println(alphaMotor);
		
		// wieso komme ich hier noch auf eine *2? Wenn ich das per Hand berechne komme ich nicht drauf?
		int alphaMotorPerMotor = (int)alphaMotor*2;
		System.out.println(alphaMotorPerMotor);
		
		
		return alphaMotorPerMotor;
		
	}
	



}
